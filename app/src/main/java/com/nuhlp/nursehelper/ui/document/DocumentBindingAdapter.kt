package com.nuhlp.nursehelper.ui.document

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import com.google.android.gms.maps.SupportMapFragment
import com.nuhlp.googlemapapi.util.map.MapUtil
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.ui.home.HomeViewModel
import kotlinx.coroutines.channels.Channel

val channel = Channel<Int>()

@BindingAdapter("bindViewModel","bindLifecycle")
fun bindDocument(view: FragmentContainerView, viewModel: DocumentViewModel, lifecycleOwner: LifecycleOwner) {
    // todo view type 확정
}
