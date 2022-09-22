package com.nuhlp.nursehelper.ui.home

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.SupportMapFragment
import com.nuhlp.googlemapapi.util.map.MapUtil
import com.nuhlp.nursehelper.R
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.utill.component.IndexRecyclerView
import com.nuhlp.nursehelper.utill.component.PlacePanel
import com.nuhlp.nursehelper.utill.useapp.DocListAdapter
import com.nuhlp.nursehelper.utill.adapter.PatientsListAdapter

import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

val channelPatient_Document = Channel<Int>()

@BindingAdapter("bindViewModel","bindLifecycle","bindMap")
fun bindMap(view: FragmentContainerView, viewModel: HomeViewModel , lifecycleOwner: LifecycleOwner, mapUtil: MapUtil ) {

    view.getFragment<SupportMapFragment>().getMapAsync(mapUtil)

    viewModel.places.asLiveData().observe(lifecycleOwner) {list ->
        Log.d("HomeBindingAdapter", "Places Update!! size: ${list.size}")
        list.minByOrNull { it.distance }?.toBusiness().let {
            if (it != null)
                viewModel.updateBusinessPlace(it)
        }
        mapUtil.setPlaceMarkers(list,mapUtil)

    }
    viewModel.businessPlace.asLiveData().observe(lifecycleOwner) {bp->
        Log.d("HomeBindingAdapter", "BusinessPlace Update!! ${bp.placeName}")
        viewModel.updatePatients(bp.bpNo)

        Log.d("HomeBindingAdapter","markers size: ${viewModel.markers.size}")
        viewModel.markers
            .filter { marker -> (marker.tag as Place).id.toInt() == bp.bpNo }
            .map { marker -> marker.showInfoWindow() }
    }
}

@BindingAdapter("bindViewModel","bindLifecycle")
fun bindPlacePanel(view: PlacePanel, viewModel: HomeViewModel , lifecycleOwner: LifecycleOwner) {
    viewModel.businessPlace.asLiveData().observe(lifecycleOwner){
        view.binding.apply {
            placeImg.setImageResource(R.drawable.ic_hospital_marker)
            placeName.text = "${it.placeName}"
            placeAddress.text = "${it.addressName}"
        }

    }
}

@BindingAdapter("bindViewModel","bindLifecycle","bindMap","bindHome")
fun bindPatientView(view: RecyclerView, viewModel: HomeViewModel , lifecycleOwner: LifecycleOwner, mapUtil: MapUtil, homeUtil: HomeUtil ) {
    homeUtil.setPatientRecyclerView(view)
    val patAdapter = view.adapter as PatientsListAdapter
    viewModel.patients.asLiveData().observe(lifecycleOwner){
        Log.d("HomeBindingAdapter","patients update!! size:${it.size}")

          if(it.isNotEmpty()) {
              patAdapter.submitList(it)
              val pat = it.first()
              viewModel.updatePatient(pat)
          }
          else {
              patAdapter.submitList(emptyList())
          }
    }

    viewModel.patient.asLiveData().observe(lifecycleOwner){ pat->
        Log.d("HomeBindingAdapter", "patientItem Update!! ${pat.name}")

        lifecycleOwner.lifecycle.coroutineScope.launch {
            viewModel.updateDocCountPerMonth(pat.patNo)
            channelPatient_Document.send(pat.patNo)
        }
    }
}

@BindingAdapter("bindViewModel","bindLifecycle","bindHome")
fun bindDocumentView(view: IndexRecyclerView, viewModel: HomeViewModel , lifecycleOwner: LifecycleOwner, homeUtil: HomeUtil ) {
    homeUtil.setDocumentRecyclerView(view)
    val docAdapter = view.adapter as DocListAdapter
    var pNo = 0

    viewModel.docCountPM.asLiveData().observe(lifecycleOwner){ list ->
        Log.d("HomeBindingAdapter", "docCountPM Update!! size: ${list.size}")
        lifecycleOwner.lifecycle.coroutineScope.launch {
            pNo = channelPatient_Document.receive()
            Log.d("HomeBindingAdapter", "receive dc pNo: $pNo")

            if (list.isNotEmpty()) {
                view.updateIndex(list, false)
                viewModel.updateDocInMonth(list.last(), pNo)
            } else {
                Log.d("HomeBindingAdapter","dc false ${list.size}")
                view.updateIndex(emptyList(), false)
                view.updateIndex(emptyList(), true)
                docAdapter.submitList(emptyList())
            }
        }
    }

    viewModel.docPM.asLiveData().observe(lifecycleOwner){ list ->
        Log.d("HomeBindingAdapter", "docPM Update!! size: ${list.size}")

        if (list.isNotEmpty()){
            view.updateIndex(viewModel.docToIndex(list), true)
            (view.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(list.lastIndex+1,0)
            docAdapter.submitList(list)
        }
        // dc list 가 비어있을 경우 업데이트가 되지않음
    }

    view.let{
        it.getPickIndexLive(true).observe(lifecycleOwner) { pickH ->
            (view.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(pickH,0)

        }
        it.getPickIndexLive(false).observe(lifecycleOwner){ pickV->
            Log.d("HomeBindingAdapter", "receive indexLive pNo: $pNo")
            viewModel.updateDocInMonth(pickV,pNo)
        }
    }
}