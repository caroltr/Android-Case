package com.truckpad.androidcase.home.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.truckpad.androidcase.R
import com.truckpad.androidcase.model.ResultData
import kotlinx.android.synthetic.main.fragment_result.*

class ResultFragment : Fragment() {

    private lateinit var resultViewModel: ResultViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        resultViewModel =
            ViewModelProviders.of(this).get(ResultViewModel::class.java)

        val root = inflater.inflate(R.layout.fragment_result, container, false)

        resultViewModel.handleData(arguments)
        resultViewModel.result.observe(viewLifecycleOwner, Observer { displayData(it) })

        return root
    }

    private fun displayData(result: ResultData) {
        tv_from.text = result.from
        tv_to.text = result.to
        tv_distance.text = result.routeResult.distance
        tv_duration.text = result.routeResult.duration
        tv_toll.text = result.routeResult.tollCost
        tv_amount_fuel.text = result.routeResult.fuelUsage
        tv_price_fuel.text = result.routeResult.fuelCost
        tv_total_price.text = ""

        // Prices
        tv_general.text = result.priceResponse.geral.toString()
        tv_granel.text = result.priceResponse.granel.toString()
        tv_neogranel.text = result.priceResponse.neogranel.toString()
        tv_frigorificada.text = result.priceResponse.frigorificada.toString()
        tv_dangerous.text = result.priceResponse.perigosa.toString()
    }
}
