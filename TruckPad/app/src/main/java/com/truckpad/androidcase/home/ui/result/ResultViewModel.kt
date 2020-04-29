package com.truckpad.androidcase.home.ui.result

import android.os.Bundle
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.truckpad.androidcase.model.ResultData
import com.truckpad.androidcase.util.Extra

class ResultViewModel : ViewModel() {

    private val _result = MutableLiveData<ResultData>()

    val result: LiveData<ResultData> = _result

    fun handleData(arguments: Bundle?) {
        arguments?.get(Extra.PRICE.value)?.let {
            (it as? ResultData)?.let { result ->
                _result.value = result
            }
        }
    }
}