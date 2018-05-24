package com.thinkdevs.hyndrate.utilities

import android.content.Context
import android.content.SharedPreferences
import android.preference.PreferenceManager



object PreferenceUtilities {

    val KEY_WATER_COUNT = "water-count"
    val KEY_CHARGING_REMINDER_COUNT = "charging-reminder-count"

    private val DEFAULT_COUNT = 0

    @Synchronized
    private fun setWaterCount(context: Context, glassesOfWater: Int) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        val editor = prefs.edit()
        editor.putInt(KEY_WATER_COUNT, glassesOfWater)
        editor.apply()
    }

    fun getWaterCount(context: Context): Int {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(KEY_WATER_COUNT, DEFAULT_COUNT)
    }

    @Synchronized
    fun incrementWaterCount(context: Context) {
        var waterCount = PreferenceUtilities.getWaterCount(context)
        PreferenceUtilities.setWaterCount(context, ++waterCount)
    }

    @Synchronized
    fun incrementChargingReminderCount(context: Context) {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        var chargingReminders = prefs.getInt(KEY_CHARGING_REMINDER_COUNT, DEFAULT_COUNT)

        val editor = prefs.edit()
        editor.putInt(KEY_CHARGING_REMINDER_COUNT, ++chargingReminders)
        editor.apply()
    }

    fun getChargingReminderCount(context: Context): Int {
        val prefs = PreferenceManager.getDefaultSharedPreferences(context)
        return prefs.getInt(KEY_CHARGING_REMINDER_COUNT, DEFAULT_COUNT)
    }
}