package com.truckpad.androidcase.main

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.truckpad.androidcase.R
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity(), MainContract.View { // OnMapReadyCallback

//    private lateinit var mMap: GoogleMap

    private lateinit var presenter: MainContract.Presenter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    private var lat: Double = 0.0
    private var lng: Double = 0.0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setPresenter(MainPresenter(this))

//        (map as SupportMapFragment).getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        checkPermissions()

        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->

                location?.let {
                    lat = it.latitude
                    lng = it.longitude
                }
            }

        btn_enter.setOnClickListener {}
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
//    override fun onMapReady(googleMap: GoogleMap) {
//        mMap = googleMap
//
//        // Add a marker in Sydney and move the camera
//        val currentLocation = LatLng(lat, lng)
//        mMap.addMarker(MarkerOptions().position(currentLocation))//.title("Marker in Sydney"))
//        mMap.moveCamera(CameraUpdateFactory.newLatLng(currentLocation))
//
//        mMap.animateCamera(CameraUpdateFactory.zoomTo( 17.0f ))
//    }

    override fun setPresenter(presenter: MainContract.Presenter) {
        this.presenter = presenter
    }

    private fun checkPermissions() {
        if (ContextCompat.checkSelfPermission(
                this@MainActivity,
                Manifest.permission.ACCESS_FINE_LOCATION
            )
            != PackageManager.PERMISSION_GRANTED
        ) {
            print("CAROL - check permission: PERMISSION NOT GRANTED")
            if (ActivityCompat.shouldShowRequestPermissionRationale(
                    this@MainActivity,
                    Manifest.permission.ACCESS_FINE_LOCATION
                )
            ) {
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
        ActivityCompat.requestPermissions(
            this@MainActivity,
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            123
        )
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {
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
