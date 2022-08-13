package com.nuhlp.googlemapapi.util.map

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
import androidx.databinding.BaseObservable
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.MutableLiveData
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.nuhlp.googlemapapi.util.PermissionPolicy
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.utill.base.binding.BaseDataBindingFragment
import com.nuhlp.nursehelper.utill.useapp.Constants
import java.util.*



abstract class BaseMapFragment<T : ViewDataBinding>: BaseDataBindingFragment<T>(),MapUtil {
    private lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var locationCallback: LocationCallback
    private var locationRequest: LocationRequest
    private var isOnGPS :Boolean
    private var isGpsToggle : Boolean
    private var isOnMapReady : Boolean
    private val isGpsButton : Boolean get() { return !isGpsToggle }
    init {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        }
        locationCallback = object: LocationCallback(){
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.let{ result ->
                    result.locations.forEach {location->
                        setLastLocation(location)
                    }
                }
            }
        }
        isOnGPS = false
        isOnMapReady = false
        /* 버튼 용도 변경 버튼/토글 */
        isGpsToggle = false

    }

    /* abstract */
    abstract val markerResourceId : Int
    abstract fun onUpdateMyLatLng(latLng: LatLng)
    abstract fun onCreateViewAfterMap()

    override fun onCreateViewAfterBinding() {
        multipleLocationPermissionRequest()
        this.onCreateViewAfterMap()
    }




    /* Map Util CallBack */

    override fun onMapReady(p0: GoogleMap) {
        mMap = p0
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        setCamera(Constants.LATLNG_DONGBAEK)

        locationSettingRequest()

        showGps(mMap)

        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)
        //updateMyLocationInit()
        isOnMapReady = true
    }
    protected fun isMapReady() = isOnMapReady
    override fun onActivityResult(result: Map<String, Boolean>) = result.forEach{
        when{
            it.key == Manifest.permission.ACCESS_COARSE_LOCATION && it.value ->{
                PermissionPolicy.defaultGrant("ACCESS_COARSE_LOCATION")
                showGps(mMap)
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
        Toast.makeText(requireActivity(), "Current location:\n$p0", Toast.LENGTH_LONG)
            .show()
    }
    override fun onMyLocationButtonClick(): Boolean {

        // Return false so that we don't consume the event and the default behavior still occurs
        // (the camera animates to the user's current position).

        when{
            isGpsToggle-> gpsTogglePolicy()
            isGpsButton-> gpsButtonPolicy()
        }
        return false
    }


    /* Activity Util */
    @SuppressLint("MissingPermission")
    private fun updateLocation() {
        if(isGpsButton)
            mMap.clear()
        fusedLocationClient.requestLocationUpdates(locationRequest,locationCallback, Looper.getMainLooper())
    }
    private fun stopLocation() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        if(isGpsToggle)
            mMap.clear()

        Log.d("HomeFragment","stopLocation()")
    }
    protected fun updateMyLocationInit(){
        if(isGpsButton)
            updateLocation()
    }
    private fun setLastLocation(lastLocation: Location) {
        mMap.clear()
        LatLng(lastLocation.latitude,lastLocation.longitude).let{
            setCamera(it)
            onUpdateMyLatLng(it)
            if(!isGpsToggle)
                stopLocation()
        }
    }
    private fun showGps(mMap:GoogleMap){
        val checkP= ContextCompat.checkSelfPermission(requireActivity(), Manifest.permission.ACCESS_COARSE_LOCATION)
        if(checkP == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
    }
    private fun gpsTogglePolicy(){
        if(!isOnGPS) {
            isOnGPS = true
            Toast.makeText(requireActivity(), "MyLocation toggle clicked : $isOnGPS", Toast.LENGTH_SHORT).show()
            updateLocation()
        }
        else
        {
            isOnGPS= false
            Toast.makeText(requireActivity(), "MyLocation button clicked : $isOnGPS", Toast.LENGTH_SHORT).show()
            stopLocation()
        }
    }
    private fun gpsButtonPolicy(){
        updateLocation()
        Toast.makeText(requireActivity(), "MyLocation button clicked", Toast.LENGTH_SHORT).show()
    }

  /*  fun setMarker(latLng: LatLng) {
        val bitmapDrawable = bitmapDescriptorFromVector(requireActivity(), markerResourceId)
        val discriptor = bitmapDrawable
        val markerOptions = MarkerOptions()
            .position(latLng)
            .icon(discriptor)
        markerOptions.setAddress()
        .title("marker in Seoul City Hall")
            .snippet("37.566418,126.977943")
        mMap.addMarker(markerOptions)
    }*/

    override fun setPlaceMarker(place: Place, callback: GoogleMap.OnMarkerClickListener) {
        val bitmapDrawable = bitmapDescriptorFromVector(requireActivity(), markerResourceId)
        val discriptor = bitmapDrawable
        val markerOptions = MarkerOptions()
            .position(place.toLatLng())
            .icon(discriptor)
            .title(place.placeName)
            .snippet(place.categoryName)
        mMap.setOnMarkerClickListener(callback)
        mMap.addMarker(markerOptions)?.tag = place
    }

    private fun setCamera(latLng: LatLng) {
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(17.5f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        mMap.moveCamera(cameraUpdate)
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
        val builder = LocationSettingsRequest.Builder().addLocationRequest(locationRequest)
        val client: SettingsClient = LocationServices.getSettingsClient(requireActivity())
        val task: Task<LocationSettingsResponse> = client.checkLocationSettings(builder.build())
        task.addOnSuccessListener { locationSettingsResponse ->
            // All location settings are satisfied. The client can initialize
            // location requests here.
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
                    Log.d("MapsActivity","========= catch")

                }
            }
        }

    }


}

