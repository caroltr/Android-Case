package com.truckpad.androidcase.home.ui.map

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapView
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.PolylineOptions
import com.truckpad.androidcase.R
import com.truckpad.androidcase.home.HomeActivity
import com.truckpad.androidcase.model.Coordinate
import kotlinx.android.synthetic.main.fragment_map.view.*

class MapFragment : Fragment(), OnMapReadyCallback {

    private lateinit var mapViewModel: MapViewModel
    private lateinit var mapView: MapView

    private var route: List<Coordinate>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mapViewModel =
            ViewModelProviders.of(this).get(MapViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_map, container, false)

        activity?.let { (it as? HomeActivity)?.let { act ->
            route = act.route
        } }

        mapView = root.map
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync(this)

        return root
    }

    override fun onMapReady(googleMap: GoogleMap) {
        route?.let {
            val polyLine = PolylineOptions()
            val builder = LatLngBounds.Builder()

            it.forEach { coordinate ->
                val latLng = LatLng(coordinate.longitude, coordinate.latitude)
                polyLine.add(latLng)
                builder.include(latLng)
            }
            polyLine.width(5f).color(Color.RED)

            val bounds = builder.build()
            val cameraUpdate = CameraUpdateFactory.newLatLngBounds(bounds, 50)

            googleMap.addPolyline(polyLine)
            googleMap.animateCamera(cameraUpdate)
        }
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onStart() {
        super.onStart()
        mapView.onStart()
    }

    override fun onStop() {
        super.onStop()
        mapView.onStop()
    }

    override fun onPause() {
        mapView.onPause()
        super.onPause()
    }

    override fun onDestroy() {
        mapView.onDestroy()
        super.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }
}
