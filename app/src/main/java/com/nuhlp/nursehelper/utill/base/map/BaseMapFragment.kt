package com.nuhlp.nursehelper.utill.base.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.IntentSender
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.Canvas
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.content.ContextCompat
import androidx.databinding.ViewDataBinding
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.nuhlp.googlemapapi.util.PermissionPolicy
import com.nuhlp.googlemapapi.util.map.MapUtil
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.utill.base.binding.BaseDataBindingFragment
import com.nuhlp.nursehelper.utill.useapp.Constants
import java.util.*



abstract class BaseMapFragment<T : ViewDataBinding>: BaseDataBindingFragment<T>(), MapUtil {

    private var _locationCallback   : LocationCallback
    private var _locationRequest    : LocationRequest

    abstract val mapViewModel : BaseMapViewModel

    init {
        _locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        _locationCallback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let{ result ->
                    result.locations.forEach {location->
                        setLastLocation(location)
                    }
                }
            }
        }
    }

    /* abstract */
    abstract val markerResourceId : Int
    abstract fun onUpdateMyLatLng(latLng: LatLng)
    abstract fun onCreateViewAfterMap()

    /* Fragment */
    override fun onCreateViewAfterBinding() {
        multipleLocationPermissionRequest()
        this.onCreateViewAfterMap()
    }


    /* Map Util CallBack */
    override fun onMapReady(p0: GoogleMap) {
        mapViewModel.mMap = p0
        mapViewModel.fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())


        locationSettingRequest()

        showGps(mapViewModel.mMap)

        mapViewModel.mMap.setOnMyLocationButtonClickListener(this)
        mapViewModel.mMap.setOnMyLocationClickListener(this)

        mapViewModel.isOnMapReady = true
    }
    protected fun isMapReady() = mapViewModel.isOnMapReady
    override fun onActivityResult(result: Map<String, Boolean>) = result.forEach{
        when{
            it.key == Manifest.permission.ACCESS_COARSE_LOCATION && it.value ->{
                PermissionPolicy.defaultGrant("ACCESS_COARSE_LOCATION")
                showGps(mapViewModel.mMap)
                updateLocation()
            }
            it.key == Manifest.permission.ACCESS_FINE_LOCATION && it.value->{
                PermissionPolicy.defaultGrant("ACCESS_FINE_LOCATION")
            }
            else ->{
                PermissionPolicy.defaultReject(it.key)
            }
        }
    }
    override fun onMyLocationClick(p0: Location) {
       // Toast.makeText(requireActivity(), "Current location:\n$p0", Toast.LENGTH_LONG).show()
    }
    override fun onMyLocationButtonClick(): Boolean {

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).

        when{
            mapViewModel.isGpsToggle-> gpsTogglePolicy()
            mapViewModel.isGpsButton-> gpsButtonPolicy()
        }
        return false
    }


    /* Fragment Util */
    @SuppressLint("MissingPermission")
    protected fun updateLocation() {
        if(mapViewModel.isGpsButton)
            mapViewModel.mMap.clear()
        mapViewModel.fusedLocationClient.requestLocationUpdates(_locationRequest,_locationCallback, Looper.getMainLooper())
    }
    private fun stopLocation() {
        mapViewModel.fusedLocationClient.removeLocationUpdates(_locationCallback)
        if(mapViewModel.isGpsToggle)
            mapViewModel.mMap.clear()

        Log.d("HomeFragment","stopLocation()")
    }
    protected fun updateMyLocationInit(){
        if(mapViewModel.isGpsButton)
            updateLocation()
    }
    private fun setLastLocation(lastLocation: Location) {
        mapViewModel.mMap.clear()


        /*  현재위치 맞춤
            LatLng(lastLocation.latitude,lastLocation.longitude).let{
            setCamera(it)
            onUpdateMyLatLng(it)
        }*/
        Constants.LATLNG_DONGBAEK.let{ //todo 지우기(위치 임시맞춤)
            setCamera(it)
            onUpdateMyLatLng(it)
        }
        if(!mapViewModel.isGpsToggle)
            stopLocation()
    }
    private fun showGps(mMap:GoogleMap){
        val checkP= ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
        if(checkP == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
    }
    private fun gpsTogglePolicy(){
        if(!mapViewModel.isOnGPS) {
            mapViewModel.isOnGPS = true
            //Toast.makeText(requireActivity(), "MyLocation toggle clicked : ${mapViewModel.isOnGPS}", Toast.LENGTH_SHORT).show()
            updateLocation()
        }
        else
        {
            mapViewModel.isOnGPS= false
            //Toast.makeText(requireActivity(), "MyLocation button clicked : ${mapViewModel.isOnGPS}", Toast.LENGTH_SHORT).show()
            stopLocation()
        }
    }
    private fun gpsButtonPolicy(){
        updateLocation()
        //Toast.makeText(requireActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show()
    }


    override fun setPlaceMarkers(places: List<Place>, callback: GoogleMap.OnMarkerClickListener) {
        val bitmapDrawable = bitmapDescriptorFromVector(requireActivity(), markerResourceId)
        val discriptor = bitmapDrawable
        val markers = mutableListOf<Marker>()
        places.forEach {place->
            val markerOptions = MarkerOptions()
                .position(place.toLatLng())
                .icon(discriptor)
                .title(place.placeName)
                .snippet(place.categoryName)
            mapViewModel.mMap.addMarker(markerOptions)?.also {marker->
                marker.tag = place
                markers.add(marker)
            }
        }
        mapViewModel.mMap.setOnMarkerClickListener(callback)
        mapViewModel.markers=markers
    }

    private fun setCamera(latLng: LatLng) {
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(17.5f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        mapViewModel.mMap.moveCamera(cameraUpdate)
    }


    /* Component Util */
    private fun bitmapDescriptorFromVector(context: Context, vectorResId: Int): BitmapDescriptor? {
        val vectorDrawable = ContextCompat.getDrawable(context, vectorResId)
        vectorDrawable!!.setBounds(0,
            0,
            vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight)
        val bitmap = Bitmap.createBitmap(vectorDrawable.intrinsicWidth,
            vectorDrawable.intrinsicHeight,
            Bitmap.Config.ARGB_8888)
        val canvas = Canvas(bitmap)
        vectorDrawable.draw(canvas)
        return BitmapDescriptorFactory.fromBitmap(bitmap)
    }
    private fun MarkerOptions.setAddress(){
        try {
            val geo =
                Geocoder(requireActivity(), Locale.getDefault())
            val addresses: List<Address> = geo.getFromLocation(
                Constants.LATLNG_DONGBAEK.latitude,
                Constants.LATLNG_DONGBAEK.longitude, 1)
            if (addresses.isEmpty()) {
                title("Waiting for Location")
            } else {
                if (addresses.size > 0) {
                    addresses[0].apply{
                        title(this.getAddressLine(0))
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace() // getFromLocation() may sometimes fail
        }
    }


     /* permission */
    private fun multipleLocationPermissionRequest(){
        val checkPermission = PermissionPolicy.location.let{ array->
            array.all { p->
                ContextCompat.checkSelfPermission(requireActivity(),p) == PackageManager.PERMISSION_GRANTED }
        }
        val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(),this)

        when {
            checkPermission -> {
                PermissionPolicy.defaultGrant("all grant")
            }
           /* 한번 거절후 다음시작부터 적용*/
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                PermissionPolicy.ration(Manifest.permission.ACCESS_COARSE_LOCATION)
                requestPermissionsLauncher.launch(PermissionPolicy.location)
            }
            else -> {
                Log.d("PermissionPolicy","new request!!")
                requestPermissionsLauncher.launch(PermissionPolicy.location)
            }
        }
    }


    private fun locationSettingRequest(){

        val REQUEST_CHECK_SETTINGS = 0x1
        val builder = LocationSettingsRequest.Builder().addLocationRequest(_locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())

        task.addOnSuccessListener(){
            // 화면 처음 생성시에만 위치 업데이트 (앱처음 x)
              if(mapViewModel.isCreateFirst) {
                  updateLocation()
                  mapViewModel.isCreateFirst = false
              }

        }
        task.addOnFailureListener { exception ->
            if (exception is ResolvableApiException){
                // Location settings are not satisfied, but this can be fixed
                // by showing the user a dialog.
                try {
                    // Show the dialog by calling startResolutionForResult(),
                    // and check the result in onActivityResult().
                    exception.startResolutionForResult(requireActivity(), REQUEST_CHECK_SETTINGS)

                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                }
            }
        }

    }



    /* 사용하지 않음 */
    fun setMarker(latLng: LatLng) {
        val bitmapDrawable = bitmapDescriptorFromVector(requireActivity(), markerResourceId)
        val discriptor = bitmapDrawable
        val markerOptions = MarkerOptions()
            .position(latLng)
            .icon(discriptor)
        markerOptions.setAddress()
        /*  .title("marker in Seoul City Hall")
              .snippet("37.566418,126.977943")*/
        mapViewModel.mMap.addMarker(markerOptions)
    }

}

