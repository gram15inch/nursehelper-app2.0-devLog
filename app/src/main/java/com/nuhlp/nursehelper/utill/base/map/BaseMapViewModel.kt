package com.nuhlp.nursehelper.utill.base.map

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
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
    lateinit var mMap: GoogleMap
    lateinit var fusedLocationClient: FusedLocationProviderClient

    private val mapRepository = MapRepository(getAppKakaoApi())
    var isOnGPS        : Boolean // toggle
    var isGpsToggle    : Boolean
    var isOnMapReady   : Boolean
    val isGpsButton    : Boolean get() { return !isGpsToggle }
    var isCreateFirst : Boolean

    init {
        isOnGPS = false      // 토글시 on/off 상태
        isOnMapReady = false // 맵뷰 할당
        isGpsToggle = false  // 토글/버튼 활성화 상태
        isCreateFirst = true
    }

    val markers = mutableListOf<Marker>()
    private val _places = MutableSharedFlow<List<Place>>()
    val places : Flow<List<Place>> = _places
    private val _businessPlace  = MutableSharedFlow<BusinessPlace>()
    val businessPlace : Flow<BusinessPlace> = _businessPlace

    fun updatePlaces(latLng: LatLng)= viewModelScope.launch{
           _places.emit(mapRepository.getPlaces("HP8",latLng))
    }

    fun updateBusinessPlace(bp: BusinessPlace)= viewModelScope.launch{
        _businessPlace.emit(bp)
    }



    /* 사용하지 않음 */
    private val _myLocation = MutableLiveData<LatLng>()
    val myLocation = _myLocation

    fun updateMyLocation(latLng: LatLng){
        _myLocation.value = latLng
    }

}

