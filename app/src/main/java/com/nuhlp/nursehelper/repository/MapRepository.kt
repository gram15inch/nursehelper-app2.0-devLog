package com.nuhlp.nursehelper.repository

import com.google.android.gms.maps.model.LatLng
import com.nuhlp.nursehelper.datasource.network.MapsApiService
import com.nuhlp.nursehelper.datasource.network.model.place.Place

class MapRepository(private val networkApi:MapsApiService) {

    suspend fun getPlaces(category:String ,latLng: LatLng):List<Place>{
      return networkApi.getPlaces(category,latLng.latitude,latLng.longitude).places
    }

}