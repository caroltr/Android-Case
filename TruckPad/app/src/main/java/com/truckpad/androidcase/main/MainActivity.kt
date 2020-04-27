package com.truckpad.androidcase.main

import android.Manifest
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.truckpad.androidcase.PermissionHandler
import com.truckpad.androidcase.R
import com.truckpad.androidcase.util.RequestCode
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View { // OnMapReadyCallback

    //    private lateinit var mMap: GoogleMap
    private val permissionHandler by lazy { PermissionHandler(this) }

    private lateinit var presenter: MainContract.Presenter
    private lateinit var fusedLocationClient: FusedLocationProviderClient

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setPresenter(MainPresenter(this))

//        (map as SupportMapFragment).getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        permissionHandler.checkPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            RequestCode.PERMISSION_LOCATION
        ) {
            println("CAROL - Permission granted")
        }

        btn_enter.setOnClickListener { calcPrice() }
    }

    private fun calcPrice() {
        val from = et_from.text.toString()
        val to = et_to.text.toString()

        val axes = et_axes.text.toString()
        val consumption = et_consumption.text.toString()

        presenter.calcPrice(from, to, axes, consumption)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>, grantResults: IntArray
    ) {

        when (requestCode) {
            RequestCode.PERMISSION_LOCATION.code -> {
                permissionHandler.handlePermissionResult(grantResults, {
                    println("CAROL - Result: Permission granted")
                }, {
                    println("CAROL - Result: Permission denied")
                })
                return
            }

            else -> {
                // Ignore all other requests.
            }
        }
    }

}
