package com.example.todoapp.utils

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

class PreferencesManager(context: Context): SharedPreferences.OnSharedPreferenceChangeListener {

    private val sharedPrefs = androidx.preference.PreferenceManager.getDefaultSharedPreferences(context)

    init {
        sharedPrefs.registerOnSharedPreferenceChangeListener(this)
    }

    var useGridLayout: Boolean
        get() = sharedPrefs.getBoolean(KEY_USE_GRID_LAYOUT, true)

        set(value) {
            sharedPrefs.edit {
                putBoolean(KEY_USE_GRID_LAYOUT, value)
                apply()
            }
        }

    companion object {
        const val KEY_USE_GRID_LAYOUT = "KEY_USE_GRID_LAYOUT"
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {}

}