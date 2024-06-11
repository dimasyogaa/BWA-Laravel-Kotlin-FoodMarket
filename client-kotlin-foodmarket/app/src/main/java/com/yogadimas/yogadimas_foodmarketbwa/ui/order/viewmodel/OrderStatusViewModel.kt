package com.yogadimas.yogadimas_foodmarketbwa.ui.order.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.transaction.Data

class OrderStatusViewModel: ViewModel() {

    private var _inProgressOrderData = MutableLiveData<ArrayList<Data>?>()
    val inProgressOrderData: LiveData<ArrayList<Data>?> = _inProgressOrderData

    private var _inPastOrderData = MutableLiveData<ArrayList<Data>?>()
    val inPastOrderData: LiveData<ArrayList<Data>?> = _inPastOrderData

    fun setInProgressOrderData(data: ArrayList<Data>?) {
        _inProgressOrderData.value = data
    }

    fun setInPastOrderData(data: ArrayList<Data>?) {
        _inPastOrderData.value = data
    }


}