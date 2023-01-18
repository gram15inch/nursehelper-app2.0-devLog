package com.nuhlp.nursehelper.repository

import android.util.Log
import com.nuhlp.nursehelper.datasource.network.MapsApiService
import com.nuhlp.nursehelper.utill.useapp.Constants
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestRepository @Inject constructor(val service :MapsApiService ) {

    init {

        CoroutineScope(Dispatchers.IO).launch {
           val list= service.getPlaces("HP8", Constants.LATLNG_DONGBAEK.latitude,Constants.LATLNG_DONGBAEK.longitude)
            if(list.places.isNotEmpty()){
                Log.d("tesRepo","size: ${list.places.size}")
            }

        }
    }
}