package com.nuhlp.nursehelper.utill.base.map

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.nursehelper.datasource.network.getAppKakaoApi
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.datasource.room.app.BusinessPlace
import com.nuhlp.nursehelper.repository.MapRepository
import kotlinx.coroutines.launch

abstract class BaseMapViewModel(application: Application) : AndroidViewModel(application) {

    private val mapRepository = MapRepository(getAppKakaoApi())

    private val _places = MutableLiveData<List<Place>>()
    val places : LiveData<List<Place>> = _places
    val businessPlace  = MutableLiveData<BusinessPlace>()
    private val _myLocation = MutableLiveData<LatLng>()
    val myLocation = _myLocation

    fun updatePlaces(latLng: LatLng){
        viewModelScope.launch{
           // _places.value = mapRepository.getPlaces("HP8",latLng)
            _places.value = emptyList()
        }
    }
    fun updateMyLocation(latLng: LatLng){
        _myLocation.value = latLng
    }
}

