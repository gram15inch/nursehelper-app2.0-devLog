package com.nuhlp.nursehelper.utill.base.map

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.nursehelper.datasource.network.getAppKakaoApi
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.datasource.room.app.BusinessPlace
import com.nuhlp.nursehelper.repository.MapRepository
import kotlinx.coroutines.launch

abstract class BaseMapViewModel : ViewModel() {

    private val mapRepository = MapRepository(getAppKakaoApi())

    private val _places = MutableLiveData<List<Place>>()
    val places : LiveData<List<Place>> = _places
    val businessPlace  = MutableLiveData<BusinessPlace>()
    private val _myLocation = MutableLiveData<LatLng>()
    val myLocation = _myLocation

    fun updatePlaces(latLng: LatLng){
        viewModelScope.launch{
           _places.value = mapRepository.getPlaces("HP8",latLng)
        }
    }

    fun updateMyLocation(latLng: LatLng){
        _myLocation.value = latLng
    }
}

