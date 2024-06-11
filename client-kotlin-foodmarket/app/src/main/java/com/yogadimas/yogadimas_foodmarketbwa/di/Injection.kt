package com.yogadimas.yogadimas_foodmarketbwa.di

import android.content.Context
import com.yogadimas.yogadimas_foodmarketbwa.data.local.database.room.FoodMarketDatabase
import com.yogadimas.yogadimas_foodmarketbwa.data.networking.HttpClient
import com.yogadimas.yogadimas_foodmarketbwa.repositories.FoodRepository

object Injection {

    fun provideRepository(context: Context): FoodRepository {
        val database = FoodMarketDatabase.getInstance(context)
        val apiService = HttpClient.getApiService()
        return FoodRepository.getInstance(database, apiService)
    }

}