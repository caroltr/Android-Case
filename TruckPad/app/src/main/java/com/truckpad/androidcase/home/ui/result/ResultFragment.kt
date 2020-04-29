package com.truckpad.androidcase.home.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.truckpad.androidcase.R
import com.truckpad.androidcase.model.Coordinate
import com.truckpad.androidcase.model.ResultData
import com.truckpad.androidcase.util.Extra
import kotlinx.android.synthetic.main.fragment_result.view.*
import kotlinx.android.synthetic.main.fragment_result.view.btn_map

import java.util.ArrayList

class ResultFragment : Fragment() {

    private lateinit var homeViewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        homeViewModel =
            ViewModelProviders.of(this).get(ResultViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_result, container, false)

        var route: List<List<Double>>? = null
        arguments?.get(Extra.PRICE.value)?.let {
            val result = it as ResultData

            route = result.routeResult.route

            root.tv_from.text = result.from
            root.tv_to.text = result.to
            root.tv_distance.text = result.routeResult.distance
            root.tv_duration.text = result.routeResult.duration
            root.tv_toll.text = result.routeResult.tollCost
            root.tv_amount_fuel.text = result.routeResult.fuelUsage
            root.tv_price_fuel.text = result.routeResult.fuelCost
            root.tv_total_price.text = ""

            // Prices
            root.tv_general.text = result.priceResponse.geral.toString()
            root.tv_granel.text = result.priceResponse.granel.toString()
            root.tv_neogranel.text = result.priceResponse.neogranel.toString()
            root.tv_frigorificada.text = result.priceResponse.frigorificada.toString()
            root.tv_dangerous.text = result.priceResponse.perigosa.toString()
        }

        root.btn_map.setOnClickListener { showMapFragment(route) }

        return root
    }

    private fun showMapFragment(route: List<List<Double>>?) {

        val r = ArrayList<Coordinate>()
        route?.forEach { list ->
            val coord = Coordinate(list[0], list[1])
            r.add(coord)
        }

        val bundle = Bundle()
        bundle.putParcelableArrayList(Extra.ROUTE.value, r)



        findNavController().navigate(R.id.action_navigation_result_to_navigation_map, bundle)
    }
}
