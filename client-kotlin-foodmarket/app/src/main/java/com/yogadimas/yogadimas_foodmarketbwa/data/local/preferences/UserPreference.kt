package com.yogadimas.yogadimas_foodmarketbwa.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object UserPreference {
    private lateinit var preferences: SharedPreferences

    private const val PREFERENCES_TOKEN = "PREFERENCES_TOKEN"
    private const val PREFERENCES_USER = "PREFERENCES_USER"

    fun initialize(context: Context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setToken(token: String) {
        preferences.edit().putString(PREFERENCES_TOKEN, token).apply()
        // HttpClient.getApiService().buildRetrofitClient(token)
    }

    fun getToken(): String? {
        return preferences.getString(PREFERENCES_TOKEN, null)
    }

    fun setUser(user: String) {
        preferences.edit().putString(PREFERENCES_USER, user).apply()
        // HttpClient.getInstance().buildRetrofitClient(user)
    }

    fun getUser(): String? {
        return preferences.getString(PREFERENCES_USER, null)
    }
}