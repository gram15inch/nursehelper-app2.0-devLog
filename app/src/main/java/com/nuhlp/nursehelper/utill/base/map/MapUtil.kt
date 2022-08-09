package com.nuhlp.googlemapapi.util.map

import android.location.Location
import androidx.activity.result.ActivityResultCallback
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.Marker

interface MapUtil : OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    GoogleMap.OnMarkerClickListener,
    ActivityResultCallback<Map<String, Boolean>> {


    override fun onMapReady(p0: GoogleMap)
    override fun onMyLocationButtonClick(): Boolean
    override fun onMyLocationClick(p0: Location)
    override fun onActivityResult(result: Map<String, Boolean>)
    override fun onMarkerClick(p0: Marker): Boolean{return false}
}