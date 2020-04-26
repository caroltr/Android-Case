package com.truckpad.androidcase.main

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.truckpad.androidcase.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), OnMapReadyCallback, MainContract.View {

    private lateinit var presenter: MainContract.Presenter
    private lateinit var mMap: GoogleMap

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setPresenter(MainPresenter(this))

        (map as SupportMapFragment).getMapAsync(this)

        checkPermissions()

        btn_enter.setOnClickListener {
            presenter.findRoute(
                -46.68664,
                -23.59496,
                -46.67678,
                -23.59867,
                5.0,
                4.4
            )
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Add a marker in Sydney and move the camera
        val sydney = LatLng(-34.0, 151.0)
        mMap.addMarker(MarkerOptions().position(sydney).title("Marker in Sydney"))
        mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney))
    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(this@MainActivity, Manifest.permission.ACCESS_FINE_LOCATION)
            != PackageManager.PERMISSION_GRANTED
        ) {
            print("CAROL - check permission: PERMISSION NOT GRANTED")
            if (ActivityCompat.shouldShowRequestPermissionRationale(this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {
                // Show an explanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

                println(" --> SHOW EXPLANATION")

                requestLocationPermission()

            } else {
                // No explanation needed, we can request the permission.
                requestLocationPermission()

                println(" --> REQUEST PERMISSION")
            }

        } else {
            // Permission has already been granted
            println("CAROL - check permission: PERMISSION ALREADY GRANTED!")
        }
    }

    private fun requestLocationPermission() {
        ActivityCompat.requestPermissions(this@MainActivity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            123)
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            123 -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    // permission was granted, yay! Do the
                    // contacts-related task you need to do.
                    println("CAROL - permission response: PERMISSION GRANTED :D")
                } else {
                    // permission denied, boo! Disable the
                    // functionality that depends on this permission.
                    println("CAROL - permission response: PERMISSION DENIED :(")
                }
                return
            }

            // Add other 'when' lines to check for other
            // permissions this app might request.
            else -> {
                // Ignore all other requests.
            }
        }
    }

}
