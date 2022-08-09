package com.nuhlp.googlemapapi.util

import android.Manifest
import android.content.pm.PackageManager
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat



object PermissionPolicy  {
    val tag = "PermissionPolicy"
    val location = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION)

    fun defaultReject(message: String="") {
        Log.d(tag,"call rejectPermission! : $message")

    }
    fun defaultGrant(message: String="") {
        Log.d(tag,"call grantPermission! : $message")
    }
    fun ration(permission: String) {
     Log.d(tag,"call ration! : $permission")
    //todo showInContextUI(...)
    }

}