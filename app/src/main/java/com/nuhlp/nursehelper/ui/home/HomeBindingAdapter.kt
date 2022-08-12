package com.nuhlp.nursehelper.ui.home

import androidx.databinding.BindingAdapter
import androidx.fragment.app.FragmentContainerView
import com.google.android.gms.maps.SupportMapFragment
import com.nuhlp.googlemapapi.util.map.MapUtil

@BindingAdapter("bindMap")
fun bindMap(view: FragmentContainerView, mapUtil: MapUtil) {
    view.getFragment<SupportMapFragment>().getMapAsync(mapUtil)
}
