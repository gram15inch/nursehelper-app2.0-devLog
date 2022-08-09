package com.nuhlp.nursehelper.utill.base.map

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.google.android.gms.maps.model.LatLng
import com.nuhlp.nursehelper.data.network.KaKaoApi
import com.nuhlp.nursehelper.data.network.model.place.Place
import com.nuhlp.nursehelper.repository.AppRepository
import kotlinx.coroutines.launch

abstract class BaseMapViewModel(application: Application) : AndroidViewModel(application) {

    abstract val appRepositoryMap :AppRepository
    private val appRepository get() =  appRepositoryMap

    private val _places = MutableLiveData<List<Place>>()
    val places : LiveData<List<Place>> = _places
    val place  = MutableLiveData<Place>()
    private val _myLocation = MutableLiveData<LatLng>()
    val myLocation = _myLocation

    fun updatePlaces(latLng: LatLng){
        viewModelScope.launch{
            _places.value = appRepository.testGetPlace(latLng)
        }
    }
    fun updateMyLocation(latLng: LatLng){
        _myLocation.value = latLng
    }
}

