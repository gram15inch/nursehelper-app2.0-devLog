package com.nuhlp.nursehelper.ui.home

import android.print.PrintDocumentAdapter
import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.*
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.SupportMapFragment
import com.nuhlp.googlemapapi.util.map.MapUtil
import com.nuhlp.nursehelper.utill.component.IndexRecyclerView
import com.nuhlp.nursehelper.utill.useapp.DocListAdapter
import com.nuhlp.nursehelper.utill.useapp.adapter.PatientsListAdapter
import kotlinx.android.synthetic.main.fragment_home.view.*
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch


@BindingAdapter("bindViewModel","bindLifecycle","bindMap")
fun bindMap(view: FragmentContainerView, viewModel: HomeViewModel , lifecycleOwner: LifecycleOwner, mapUtil: MapUtil ) {

    view.getFragment<SupportMapFragment>().getMapAsync(mapUtil)

    viewModel.places.asLiveData().observe(lifecycleOwner) {list ->
        Log.d("HomeFragment", "Places Update!! size: ${list.size}")
            list.minByOrNull { it.distance }?.toBusiness().let {
                if (it != null)
                    viewModel.updateBusinessPlace(it)
            }
            list.forEach {
                mapUtil.setPlaceMarker(it,mapUtil)
            }
    }
    viewModel.businessPlace.asLiveData().observe(lifecycleOwner) {
            Log.d("HomeFragment", "BusinessPlace Update!! ${it.placeName}")
           viewModel.updatePatients(it.bpNo)
    }
}

@BindingAdapter("bindViewModel","bindLifecycle","bindMap","bindHome")
fun bindPatientView(view: RecyclerView, viewModel: HomeViewModel , lifecycleOwner: LifecycleOwner, mapUtil: MapUtil, homeUtil: HomeUtil ) {
    homeUtil.setPatientRecyclerView(view)
    val patAdapter = view.adapter as PatientsListAdapter
    val docAdapter = view.adapter as PatientsListAdapter

    viewModel.patients.asLiveData().observe(lifecycleOwner){
        Log.d("HomeFragment","patients update!! size:${it.size}")

          if(it.isEmpty()) {
              patAdapter.submitList(emptyList())
              docAdapter.submitList(emptyList())
          }
          else {
              patAdapter.submitList(it)
              val pat = it.first()
              viewModel.updatePatient(pat)
          }
    }

    viewModel.patient.asLiveData().observe(lifecycleOwner){ pat->
        Log.d("HomeFragment", "patientItem Update!! ${pat.name}")
        viewModel.updateDocCountPerMonth(pat.patNo)
    }
}

@BindingAdapter("bindViewModel","bindLifecycle","bindHome")
fun bindDocumentView(view: IndexRecyclerView, viewModel: HomeViewModel , lifecycleOwner: LifecycleOwner, homeUtil: HomeUtil ) {
    homeUtil.setDocumentRecyclerView(view)
    val docAdapter = view.adapter as DocListAdapter

    viewModel.docCountPM.asLiveData().observe(lifecycleOwner){ list ->
        Log.d("HomeFragment", "docCountPM Update!! size: ${list.size}")
        if (list.isNotEmpty()){
            view.index_recyclerView.updateIndex(list, false)
            viewModel.updateDocInMonth(list.last())
        }else{
            docAdapter.submitList(emptyList())
        }
    }

    viewModel.docPM.asLiveData().observe(lifecycleOwner){ list ->
        Log.d("HomeFragment", "docPM Update!! size: ${list.size}")

        if (list.isNotEmpty()){
            view.index_recyclerView.updateIndex(viewModel.docToIndex(list), true)
            (view.index_recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(list.lastIndex+1,0)
            docAdapter.submitList(list)
            //recyclerViewUpdate(list.last(), false)
        }else
            docAdapter.submitList(emptyList())
    }

    view.let{
        it.getPickIndexLive(true).observe(lifecycleOwner) { pickH ->
            (view.index_recyclerView.layoutManager as LinearLayoutManager).scrollToPositionWithOffset(pickH,0)

        }
        it.getPickIndexLive(false).observe(lifecycleOwner){ pickV->
            viewModel.updateDocInMonth(pickV)
        }
    }
}