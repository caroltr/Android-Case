package com.truckpad.androidcase.home.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.truckpad.androidcase.R
import com.truckpad.androidcase.model.PriceResponse
import kotlinx.android.synthetic.main.fragment_search.*


class SearchFragment : Fragment() {

    private lateinit var searchViewModel: SearchViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        searchViewModel =
            ViewModelProviders.of(this).get(SearchViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_search, container, false)

        val btnEnter: Button = root.findViewById(R.id.btn_enter)
        btnEnter.setOnClickListener { calcPrice() }

        searchViewModel.result.observe(viewLifecycleOwner, Observer {
            showResultFragment(it)
        })

        return root
    }

    private fun calcPrice() {
        val from = et_from.text.toString()
        val to = et_to.text.toString()

        val axis = et_axes.text.toString()
        val consumption = et_consumption.text.toString()
        val fuelValue = et_fuel_value.text.toString()

        searchViewModel.calcPrice(from, to, axis, consumption, fuelValue)
    }

    private fun showResultFragment(result: PriceResponse) {
        val bundle = Bundle()
        bundle.putParcelable("price_extra", result)

        findNavController().navigate(R.id.action_navigation_search_to_navigation_result2, bundle)
    }
}
