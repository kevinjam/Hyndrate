package com.thinkdevs.hyndrate

import android.content.*
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import android.preference.PreferenceManager
import android.view.View
import com.thinkdevs.hyndrate.utilities.PreferenceUtilities
import kotlinx.android.synthetic.main.activity_main.*
import com.thinkdevs.hyndrate.sync.ReminderTasks
import android.support.v4.view.accessibility.AccessibilityEventCompat.setAction
import com.thinkdevs.hyndrate.sync.WaterReminderIntentService
import android.content.Intent.ACTION_POWER_CONNECTED
import android.content.Intent.ACTION_POWER_DISCONNECTED
import com.thinkdevs.hyndrate.sync.ReminderUtilities
import com.thinkdevs.hyndrate.utilities.NotificationUtils
import android.content.Intent




class MainActivity : AppCompatActivity() , SharedPreferences.OnSharedPreferenceChangeListener{



    private var mToast: Toast? = null
    var mchargingIntentFilter:IntentFilter?= null
    var mChargingReceiver:ChargingBroadcastReceiver?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /** Set the original values in the UI **/
        updateWaterCount()
        updateChargingReminderCount()

        ReminderUtilities.scheduleChargingReminder(this)
        mchargingIntentFilter = IntentFilter()
        mchargingIntentFilter!!.addAction(ACTION_POWER_CONNECTED)
        mchargingIntentFilter!!.addAction(ACTION_POWER_DISCONNECTED)
        mChargingReceiver = ChargingBroadcastReceiver()

        /** Setup the shared preference listener **/
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.registerOnSharedPreferenceChangeListener(this)


        testNofication.setOnClickListener {
            NotificationUtils.remindUserBecauseCharging(this)
            println("Notification::::: " +NotificationUtils.remindUserBecauseCharging(this).toString())
        }
    }

    /**
     * Updates the TextView to display the new water count from SharedPreferences
     */
    private fun updateWaterCount() {
        val waterCount = PreferenceUtilities.getWaterCount(this)
        tv_water_count.text = waterCount.toString() + ""
    }

    /**
     * Updates the TextView to display the new charging reminder count from SharedPreferences
     */
    private fun updateChargingReminderCount() {
        val chargingReminders = PreferenceUtilities.getChargingReminderCount(this)
        val formattedChargingReminders = resources.getQuantityString(
                R.plurals.charge_notification_count, chargingReminders, chargingReminders)
        tv_charging_reminder_count.text = formattedChargingReminders

    }


    fun showcharging(ischarging:Boolean){
        if (ischarging) {
            iv_power_increment.setImageResource(R.drawable.ic_plug_red)

        } else {
            iv_power_increment.setImageResource(R.drawable.plug)
        }
    }

    /**
     * Adds one to the water count and shows a toast
     */
    fun incrementWater(view: View) {
        mToast?.cancel()
        mToast = Toast.makeText(this, R.string.water_chug_toast, Toast.LENGTH_SHORT)
        mToast!!.show()
        // TODO (15) Create an explicit intent for WaterReminderIntentService
        // TODO (16) Set the action of the intent to ACTION_INCREMENT_WATER_COUNT
        // TODO (17) Call startService and pass the explicit intent you just created

        // COMPLETED (15) Create an explicit intent for WaterReminderIntentService
        val incrementWaterCountIntent = Intent(this, WaterReminderIntentService::class.java)
        // COMPLETED (16) Set the action of the intent to ACTION_INCREMENT_WATER_COUNT
        incrementWaterCountIntent.action = ReminderTasks.ACTION_INCREMENT_WATER_COUNT
        // COMPLETED (17) Call startService and pass the explicit intent you just created
        startService(incrementWaterCountIntent)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        if (PreferenceUtilities.KEY_WATER_COUNT.equals(key)) {
            updateWaterCount()
        } else if (PreferenceUtilities.KEY_CHARGING_REMINDER_COUNT.equals(key)) {
            updateChargingReminderCount()
        }
    }


    override fun onResume() {
        super.onResume()
        registerReceiver(mChargingReceiver, mchargingIntentFilter)
    }

    override fun onPause() {
        super.onPause()
        registerReceiver(mChargingReceiver, mchargingIntentFilter)

    }

    override fun onDestroy() {
        super.onDestroy()
        /** Cleanup the shared preference listener  */
        val prefs = PreferenceManager.getDefaultSharedPreferences(this)
        prefs.unregisterOnSharedPreferenceChangeListener(this)
    }


    // COMPLETED (2) Create an inner class called ChargingBroadcastReceiver that extends BroadcastReceiver
    inner class ChargingBroadcastReceiver : BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val action = intent!!.action
            val isCharging = action == ACTION_POWER_CONNECTED

            showcharging(isCharging)

        }

    }
}


