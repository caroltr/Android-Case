package com.truckpad.androidcase.home.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.truckpad.androidcase.R
import com.truckpad.androidcase.model.PriceResponse
import kotlinx.android.synthetic.main.fragment_result.view.*

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

        arguments?.get("price_extra")?.let {
            val prices = it as PriceResponse
            print(prices)

            root.tv_axis.text = prices.geral.toString()
        }

        return root
    }
}
