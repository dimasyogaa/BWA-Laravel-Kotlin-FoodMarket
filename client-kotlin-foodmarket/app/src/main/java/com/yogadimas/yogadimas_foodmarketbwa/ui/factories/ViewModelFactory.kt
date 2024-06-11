package com.yogadimas.yogadimas_foodmarketbwa.ui.factories

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.yogadimas.yogadimas_foodmarketbwa.di.Injection
import com.yogadimas.yogadimas_foodmarketbwa.ui.home.viewmodel.HomeFoodTypesViewmodel

@Suppress("UNCHECKED_CAST")
class ViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeFoodTypesViewmodel::class.java)) {
            return HomeFoodTypesViewmodel(Injection.provideRepository(context)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}