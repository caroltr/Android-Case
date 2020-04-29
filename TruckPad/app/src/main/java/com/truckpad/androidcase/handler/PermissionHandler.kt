package com.truckpad.androidcase.handler

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.truckpad.androidcase.util.RequestCode

class PermissionHandler(private val context: Context, private val fragment: Fragment) {

    fun checkPermission(
        permission: String,
        requestCode: RequestCode,
        onPermissionGranted: () -> Unit
    ) {
        if (ContextCompat
                .checkSelfPermission(context, permission)
            != PackageManager.PERMISSION_GRANTED
        ) {
            if (fragment.shouldShowRequestPermissionRationale(permission)
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
        fragment.requestPermissions(
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            requestCode.code
        )
    }
}