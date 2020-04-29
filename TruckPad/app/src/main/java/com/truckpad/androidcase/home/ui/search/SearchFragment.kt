package com.truckpad.androidcase.home.ui.search

import android.Manifest
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.android.gms.location.LocationServices
import com.truckpad.androidcase.PermissionHandler
import com.truckpad.androidcase.R
import com.truckpad.androidcase.model.ResultData
import com.truckpad.androidcase.util.Extra
import com.truckpad.androidcase.util.RequestCode
import kotlinx.android.synthetic.main.fragment_search.*
import kotlinx.android.synthetic.main.fragment_search.view.*

class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel
    private val permissionHandler by lazy {
        activity?.let {
            PermissionHandler(it)
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        permissionHandler?.checkPermission(
            Manifest.permission.ACCESS_FINE_LOCATION,
            RequestCode.PERMISSION_LOCATION
        ) {
            activity?.let {
                val fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)
                fusedLocationClient.lastLocation.addOnSuccessListener { location ->
                    searchViewModel.getAddress(location.latitude, location.longitude)
                }
            }
        }

        val btnEnter: Button = root.findViewById(R.id.btn_enter)
        btnEnter.setOnClickListener {
            startLoad(root)
            calcPrice()
        }

        setObservers(root)

        return root
    }

    private fun setObservers(root: View) {
        searchViewModel.fromAddress.observe(viewLifecycleOwner, Observer {
            root.et_from.setText(it)
        })

        searchViewModel.result.observe(viewLifecycleOwner, Observer {
            stopLoad(root)
            showResultFragment(it)
        })

        searchViewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            stopLoad(root)
            setError(root, it)
        })
    }

    private fun calcPrice() {
        val from = et_from.text.toString()
        val to = et_to.text.toString()

        val axis = et_axes.text.toString()
        val consumption = et_consumption.text.toString()
        val fuelValue = et_fuel_value.text.toString()

        searchViewModel.calcPrice(from, to, axis, consumption, fuelValue)
    }

    private fun showResultFragment(result: ResultData) {
        val bundle = Bundle()
        bundle.putParcelable(Extra.PRICE.value, result)

        findNavController().navigate(R.id.action_navigation_search_to_navigation_result2, bundle)
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when (requestCode) {
            RequestCode.PERMISSION_LOCATION.code -> {
                permissionHandler?.handlePermissionResult(grantResults, {
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

    private fun startLoad(view: View) {
        view.tv_error.visibility = View.GONE
        view.pb_load.visibility = View.VISIBLE
    }

    private fun stopLoad(view: View) {
        view.pb_load.visibility = View.GONE
    }

    private fun setError(view: View, message: String) {
        view.tv_error.visibility = View.VISIBLE
        view.tv_error.text = message
    }
}
