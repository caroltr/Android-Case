package com.truckpad.androidcase.home.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.truckpad.androidcase.R
import com.truckpad.androidcase.model.PriceResponse
import com.truckpad.androidcase.model.RouteData
import com.truckpad.androidcase.util.asReais
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
        setObservers()

        return root
    }

    private fun setObservers() {
        resultViewModel.from.observe(viewLifecycleOwner, Observer { tv_from.text = it })
        resultViewModel.to.observe(viewLifecycleOwner, Observer { tv_to.text = it })
        resultViewModel.prices.observe(viewLifecycleOwner, Observer { displayPrices(it) })
        resultViewModel.routeData.observe(viewLifecycleOwner, Observer { displayData(it) })
    }

    private fun displayPrices(prices: PriceResponse) {
        tv_general.text = prices.geral.asReais()
        tv_granel.text = prices.granel.asReais()
        tv_neogranel.text = prices.neogranel.asReais()
        tv_frigorificada.text = prices.frigorificada.asReais()
        tv_dangerous.text = prices.perigosa.asReais()
    }

    private fun displayData(data: RouteData) {
        tv_distance.text = data.distance
        tv_duration.text = data.duration
        tv_toll.text = data.tollCost
        tv_amount_fuel.text = data.fuelUsage
        tv_price_fuel.text = data.fuelCost
        tv_total.text = data.total
    }
}
