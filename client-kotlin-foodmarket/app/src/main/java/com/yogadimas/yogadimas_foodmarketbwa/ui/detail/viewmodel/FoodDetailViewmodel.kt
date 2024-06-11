package com.yogadimas.yogadimas_foodmarketbwa.ui.detail.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.model.response.home.Data

class FoodDetailViewmodel: ViewModel() {

    private var detailFood = MutableLiveData<Data>()


    fun setDetailFood(data: Data) {
        detailFood.value = data
    }

    fun getDetailFood(): LiveData<Data> {
        Log.e("TAG", "getDetailFood: ${detailFood.value}", )
        return detailFood
    }

}