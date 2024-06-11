package com.yogadimas.yogadimas_foodmarketbwa

import androidx.multidex.MultiDexApplication
import com.yogadimas.yogadimas_foodmarketbwa.data.local.preferences.TabTypeFoodPreference
import com.yogadimas.yogadimas_foodmarketbwa.data.local.preferences.UserPreference

class FoodMarket : MultiDexApplication() {

    /*
    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    private fun getPreferences() : SharedPreferences {
        return PreferenceManager.getDefaultSharedPreferences(this)
    }

    fun setToken(token:String) {
        getPreferences().edit().putString("PREFERENCES_TOKEN", token).apply()
        HttpClient.getInstance().buildRetrofitClient(token)
    }

    fun getToken(): String? {
        return getPreferences().getString("PREFERENCES_TOKEN", null)
    }

    fun setUser(user:String) {
        getPreferences().edit().putString("PREFERENCES_USER", user).apply()
        HttpClient.getInstance().buildRetrofitClient(user)
    }

    fun getUser(): String? {
        return getPreferences().getString("PREFERENCES_USER", null)
    }

    companion object {
        lateinit var instance : FoodMarket

        fun getApp() : FoodMarket {
            return instance
        }
    }*/

    /*
    override fun onCreate() {
        super.onCreate()
        UserPreference.initialize(this)
    }

    companion object {
        val instance: FoodMarket by lazy { FoodMarket() }
    }*/

    override fun onCreate() {
        super.onCreate()
        instance = this
        UserPreference.initialize(this)
        TabTypeFoodPreference.initialize(this)
    }

    companion object {
        lateinit var instance: FoodMarket
            private set
    }


}

