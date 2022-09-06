package com.nuhlp.nursehelper.ui.quick

import android.util.Log
import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.asLiveData
import androidx.lifecycle.observe
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.maps.SupportMapFragment
import com.nuhlp.googlemapapi.util.map.MapUtil
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.ui.home.HomeViewModel


@BindingAdapter("bindViewModel","bindLifecycle")
fun bindQuickTask(view: RecyclerView, viewModel: HomeViewModel, lifecycleOwner: LifecycleOwner) {

}
