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
import android.os.Bundle
import android.os.Looper
import android.util.Log
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.common.api.ResolvableApiException
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import com.google.android.gms.tasks.Task
import com.nuhlp.googlemapapi.util.PermissionPolicy
import com.nuhlp.googlemapapi.util.map.MapUtil
import com.nuhlp.nursehelper.datasource.network.model.place.Place
import com.nuhlp.nursehelper.utill.useapp.Constants.LATLNG_DONGBAEK
import java.util.*

/* map api 학습 일지

  1. 위치 요청 -> 권한 필요
  2. 권한 요청 -> ActivityResultLauncher 필요
  3. ARL 요청 -> ActivityResult 필요
  4. AR 요청 -> Contracts, Callback 필요
    4-1. Contracts 학습 ->  Intent 생성, 결과코드 해독 등
    4-2. Callback 학습 -> 결과처리 :: 호출 액티비티가 재시작 되서 작업순서가 사라져도 결과를 처리할 수 있게 결과처리함수를 직접주입하기 위해 사용

 ** 7/31 **
  1. 더 자세한 위치 요청 -> 여러개의 권한 필요 (자세한 위치 단독으로 요쳥 불가 사용자가 정확도를 선택 할 수 있어야함 [ver>31])
  2. 여러개의 권한 요청 -> AL 생성시 여러개의 권한을 담당,처리할 수 있는 Contracts , Callback 필요
    2-1. Contracts -> ActivityResultContracts.RequestMultiplePermissions()
    2-2. Callback  -> (Map<String,Boolean>) -> Unit 타입으로 만든후 권한별 승인여부 확인후 개별처리하는 로직 생성

 ** 8/1 ~ 8/2 **
  1. 위치 요청(권한승인후) -> 통합위치정보 제공자 클라이언트 필요(FusedLocationProviderClient)
  2. 통제자 클라이언트 요청 -> 통제자 필요 (통제자에 위치 결과처리 콜백을 전달해서 위치 업데이트#[1])
    2-1. 통제자 -> 구글 play 서비스에서 위치확인을 위해 공통적으로 사용하는 시스템자원접근 API (구글 play api 를 사용하는 앱끼리 최신위치정보를 공유::자원절약)
  3. 통제자 요청 -> 기기에 시스템설정(gps 등..) 확인 필요
  4. 시스템설정 확인 요청 ->  SettingsClient, Task<LocationSettingsResponse> 필요
    4-1. SettingsClient -> 시스템설정을 변경할수있는 접근자로 앱과 연결해 사용 todo 확인필요
    4-2. Task<LocationSettingsResponse> -> 시스템설정 확인 결과로 확인여부에 따라 실행할수있는 다른 리스너#[5]를 붙여 사용
        * SC.checkLocationSettings 로 생성 (파라미터에 넣을 LocationSettingsRequest, LocationRequest 필요)
        4-2-1. LocationRequest -> 위치를 특정하는데 필요한 요청서(간격/정확도/전력)
            * .create() 메서드로 인스턴스화 후 설정값 초기화
               - interval = 10000  (기본 업데이트 간격)
               - fastestInterval = 5000 (앱이 사용할 수 있는 가장빠른 간격)
               - priority = LocationRequest.PRIORITY_HIGH_ACCURACY (정확도/전력소비량) :: 앱의 권한 정확도와 설정의 간격들의 조합
        4-2-2. LocationSettingsRequest -> 하나이상의 LocationRequest 의 추가 필요 .addLocationRequest(locationRequest)로 추가
            * .Builder() 메서드로 인스턴스화 후 추가 (후에 파라미터로 넣을때 .build() 사용)
    4-3. Task 로 결과 확인
  5. Task 로 결과처리 리스너 연결 (성공/addOnSuccessListener, 실패/addOnFailureListener)
    5-1. 성공 -> 여기부터 현재위치 접근 가능
    5-2. 실패 -> 사용자에게 시스템 설정을 바로 수정할 수 있는 선택창 실행
        5-2-1. 실패 리스너 에서 파라미터로 받은 예외에서 ResolvableApiException 타입인지확인
        5-2-2. 타입 확인된 예외로 startResolutionForResult 호출해 설정을 수정


 ** 8/3 **
  1. 통제자로 위치 업데이트 -> requestLocationUpdates(*p) 필요
    1-1. requestLocationUpdates(*p) -> 요청서사양에 따른 위치가 업데이트될때마나 실행될 콜백을 전달 하는것으로 위치 업데이트처리
    *p 에 locationRequest,locationCallback, Looper 넣어서 생성
        1-1-1. locationRequest -> 요청서@[4-2-1]
        1-1-2. locationCallback ->  LocationResult[Location 포함]을 파라미터로 받아 위치에 따른 처리를 하는 메서드를 가진 추상클래스
            * onLocationResult(*p) 를 오버라이드해 생성
        1-1-3. Looper -> 앱의 ui 업데이트시 필요한 메세지 전달을 위한 앱루퍼 todo 확인 필요
  2. 통제자로 위치 업데이트 중지 -> removeLocationUpdates(*p) 필요
    2-2. removeLocationUpdates(*p) -> *p 에 locationCallback@[1-1-2] 넣어 사용

  3.
* */


abstract class BaseMapActivity :AppCompatActivity(), MapUtil {
    lateinit var mMap: GoogleMap
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var locationCallback: LocationCallback
    private var locationRequest: LocationRequest
    private var isOnGPS :Boolean
    private var isGpsToggle : Boolean
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

        /* 버튼 용도 변경 버튼/토글 */
        isGpsToggle = false

    }

    /* abstract */
    abstract val markerResourceId : Int
    abstract val mapFragmentId : Int
    abstract fun onUpdateMyLatLng(latLng: LatLng)
    abstract fun onCreateAfter(savedInstanceState: Bundle?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        multipleLocationPermissionRequest()
        onCreateAfter(savedInstanceState)
        val mapFragment = supportFragmentManager
            .findFragmentById(mapFragmentId) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }


    /* Map Util CallBack */

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        setCamera(LATLNG_DONGBAEK)

        locationSettingRequest()

        showGps(mMap)

        mMap.setOnMyLocationButtonClickListener(this)
        mMap.setOnMyLocationClickListener(this)

    }
    override fun onActivityResult(permissions: Map<String, Boolean>) = permissions.forEach{
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
    override fun onMyLocationClick(location: Location) {
        Toast.makeText(this, "Current location:\n$location", Toast.LENGTH_LONG)
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
    fun stopLocation() {
        fusedLocationClient.removeLocationUpdates(locationCallback)
        if(isGpsToggle)
            mMap.clear()
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
        val checkP= ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if(checkP == PackageManager.PERMISSION_GRANTED) {
            mMap.isMyLocationEnabled = true
        }
    }
    private fun gpsTogglePolicy(){
        if(!isOnGPS) {
            isOnGPS = true
            Toast.makeText(this, "MyLocation toggle clicked : $isOnGPS", Toast.LENGTH_SHORT).show()
            updateLocation()
        }
        else
        {
            isOnGPS= false
            Toast.makeText(this, "MyLocation button clicked : $isOnGPS", Toast.LENGTH_SHORT).show()
            stopLocation()
        }
    }
    private fun gpsButtonPolicy(){
        updateLocation()
        Toast.makeText(this, "MyLocation button clicked", Toast.LENGTH_SHORT).show()
    }

    fun setMarker(latLng: LatLng) {
        val bitmapDrawable = bitmapDescriptorFromVector(this, markerResourceId)
        val discriptor = bitmapDrawable
        val markerOptions = MarkerOptions()
            .position(latLng)
            .icon(discriptor)
        markerOptions.setAddress()
        /*.title("marker in Seoul City Hall")
            .snippet("37.566418,126.977943")*/
        mMap.addMarker(markerOptions)
    }
    fun setPlaceMarker(place: Place, callback: GoogleMap.OnMarkerClickListener) {
        val bitmapDrawable = bitmapDescriptorFromVector(this, markerResourceId)
        val discriptor = bitmapDrawable
        val markerOptions = MarkerOptions()
            .position(place.toLatLng())
            .icon(discriptor)
            .title(place.placeName)
            .snippet(place.categoryName)
        mMap.setOnMarkerClickListener(callback)
        mMap.addMarker(markerOptions)?.tag = place
    }

    fun setCamera(latLng: LatLng) {
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
                Geocoder(this@BaseMapActivity, Locale.getDefault())
            val addresses: List<Address> = geo.getFromLocation(LATLNG_DONGBAEK.latitude,LATLNG_DONGBAEK.longitude, 1)
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
                ContextCompat.checkSelfPermission(this,p) == PackageManager.PERMISSION_GRANTED }
        }
        val requestPermissionsLauncher = registerForActivityResult(ActivityResultContracts.RequestMultiplePermissions(),this)

        when {
            checkPermission -> {
                PermissionPolicy.defaultGrant("all grant")
            }
            /*한번 거절후 다음시작부터 적용*/
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
        val client: SettingsClient = LocationServices.getSettingsClient(this)
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
                    exception.startResolutionForResult(this, REQUEST_CHECK_SETTINGS)

                } catch (sendEx: IntentSender.SendIntentException) {
                    // Ignore the error.
                    Log.d("MapsActivity","========= catch")

                }
            }
        }

    }


    // ***** 사용안함 *****
    private fun withCurrentLatLng(callback:(LatLng)->Unit) {
        val checkP = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        if (checkP == PackageManager.PERMISSION_GRANTED) {
            fusedLocationClient.lastLocation.addOnSuccessListener {
                callback(LatLng(it.latitude, it.longitude))
                Log.d("MapsActivity","${it.latitude} ${it.longitude}")
            }
        }
    }
    private fun soloLocationPermissionRequest(){
        val checkPermission = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION)
        val requestPermissionLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){}
        when {
            checkPermission == PackageManager.PERMISSION_GRANTED -> {
                PermissionPolicy.defaultGrant("")
            }
            /*한번 거절후 다음시작부터 적용*/
            shouldShowRequestPermissionRationale(Manifest.permission.ACCESS_COARSE_LOCATION) -> {
                PermissionPolicy.ration(Manifest.permission.ACCESS_COARSE_LOCATION)
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
            else -> {
                requestPermissionLauncher.launch(Manifest.permission.ACCESS_COARSE_LOCATION)
            }
        }
    }
    fun moveCameraLauncher(map:GoogleMap,latLng: LatLng){
        val cameraPosition = CameraPosition.Builder()
            .target(latLng)
            .zoom(17.0f)
            .build()
        val cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition)
        map.moveCamera(cameraUpdate)
    }


}