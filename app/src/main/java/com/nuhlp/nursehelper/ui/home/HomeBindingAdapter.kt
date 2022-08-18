package com.nuhlp.nursehelper.ui.home

import android.util.Log
import androidx.cardview.widget.CardView
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
import com.nuhlp.nursehelper.utill.useapp.DocListAdapter
import com.nuhlp.nursehelper.utill.useapp.adapter.PatientsListAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

val channelPatient_Document = Channel<Int>()

@BindingAdapter("bindViewModel","bindLifecycle")
fun bindPlaceCardView(view: CardView, viewModel: HomeViewModel , lifecycleOwner: LifecycleOwner) {
    val placeName = view.placeNameCardView
    val address = view.addressCardView
    val imgIcon = view.placeImgCardView

    viewModel.businessPlace.asLiveData().observe(lifecycleOwner){
        placeName.text = "${it.placeName}"
        address.text = "${it.addressName}"
    }
    imgIcon.setImageResource(R.drawable.ic_hospital_marker)

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
                view.index_recyclerView.updateIndex(list, false)
                viewModel.updateDocInMonth(list.last(), pNo)
            } else {
                Log.d("HomeBindingAdapter","dc false ${list.size}")
                view.index_recyclerView.updateIndex(emptyList(), false)
                view.index_recyclerView.updateIndex(emptyList(), true)
                docAdapter.submitList(emptyList())
            }
        }
    }

    viewModel.docPM.asLiveData().observe(lifecycleOwner){ list ->
        Log.d("HomeBindingAdapter", "docPM Update!! size: ${list.size}")

        if (list.isNotEmpty()){
            view.index_recyclerView.updateIndex(viewModel.docToIndex(list), true)
            (view.index_recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(list.lastIndex+1,0)
            docAdapter.submitList(list)
        }
        // dc list 가 비어있을 경우 업데이트가 되지않음
    }

    view.let{
        it.getPickIndexLive(true).observe(lifecycleOwner) { pickH ->
            (view.index_recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(pickH,0)

        }
        it.getPickIndexLive(false).observe(lifecycleOwner){ pickV->
            Log.d("HomeBindingAdapter", "receive indexLive pNo: $pNo")
            viewModel.updateDocInMonth(pickV,pNo)
        }
    }
}