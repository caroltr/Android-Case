package com.truckpad.androidcase

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.truckpad.androidcase.util.RequestCode

class PermissionHandler(private val activity: Activity) {

    fun checkPermission(
        permission: String,
        requestCode: RequestCode,
        onPermissionGranted: () -> Unit
    ) {
        if (ContextCompat
                .checkSelfPermission(activity, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    activity, permission
                )
            ) {
                requestLocationPermission(requestCode)
//                onShowExplanation()
            } else {
                requestLocationPermission(requestCode)
            }

        } else {
            onPermissionGranted()
        }
    }

    fun handlePermissionResult(
        grantResults: IntArray,
        onPermissionGranted: () -> Unit,
        onPermissionDenied: () -> Unit
    ) {
        if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
            onPermissionGranted()
        } else {
            onPermissionDenied()
        }
    }

    private fun requestLocationPermission(requestCode: RequestCode) {
        ActivityCompat.requestPermissions(
            activity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestCode.code
        )
    }
}