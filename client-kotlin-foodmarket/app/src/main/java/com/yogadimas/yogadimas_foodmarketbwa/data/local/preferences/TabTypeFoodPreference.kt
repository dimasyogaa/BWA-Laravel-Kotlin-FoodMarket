package com.yogadimas.yogadimas_foodmarketbwa.data.local.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.preference.PreferenceManager

object TabTypeFoodPreference {
    private lateinit var preferences: SharedPreferences

    private const val PREFERENCES_TAB = "PREFERENCES_TAB"
    private const val PREFERENCES_STARTED = "PREFERENCES_STARTED"

    fun initialize(context: Context) {
        preferences = PreferenceManager.getDefaultSharedPreferences(context)
    }

    fun setTab(name: String?) {
        preferences.edit().putString(PREFERENCES_TAB, name).apply()
    }

    fun getTab(): String? {
        return preferences.getString(PREFERENCES_TAB, null)
    }

    fun setStarted(boolean: Boolean) {
        preferences.edit().putBoolean(PREFERENCES_STARTED, boolean).apply()
    }

    fun getStarted(): Boolean {
        return preferences.getBoolean(PREFERENCES_STARTED, false)
    }
}