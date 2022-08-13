package com.nuhlp.nursehelper.utill.base.map

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.nursehelper.datasource.network.getAppKakaoApi
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.datasource.room.app.BusinessPlace
import com.nuhlp.nursehelper.repository.MapRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

abstract class BaseMapViewModel : ViewModel() {

    private val mapRepository = MapRepository(getAppKakaoApi())

    private val _places = MutableSharedFlow<List<Place>>()
    val places : Flow<List<Place>> = _places
    private val _businessPlace  = MutableSharedFlow<BusinessPlace>()
    val businessPlace : Flow<BusinessPlace> = _businessPlace

    private val _myLocation = MutableLiveData<LatLng>()
    val myLocation = _myLocation
    fun updatePlaces(latLng: LatLng)= viewModelScope.launch{
           _places.emit(mapRepository.getPlaces("HP8",latLng))
        val a = MutableSharedFlow<String>()
    }

    fun updateBusinessPlace(bp: BusinessPlace)= viewModelScope.launch{
        _businessPlace.emit(bp)
    }
    fun updateMyLocation(latLng: LatLng){
        _myLocation.value = latLng
    }

}

