package com.thinkdevs.hyndrate.sync

import android.content.Context
import com.thinkdevs.hyndrate.utilities.Constants
import com.thinkdevs.hyndrate.utilities.NotificationUtils
import com.thinkdevs.hyndrate.utilities.PreferenceUtilities

class ReminderTasks {

    // TODO (1) Create a class called ReminderTasks

// TODO (2) Create a public static constant String called ACTION_INCREMENT_WATER_COUNT

// TODO (6) Create a public static void method called executeTask
// TODO (7) Add a Context called context and String parameter called action to the parameter list

// TODO (8) If the action equals ACTION_INCREMENT_WATER_COUNT, call this class's incrementWaterCount

// TODO (3) Create a private static void method called incrementWaterCount
// TODO (4) Add a Context called context to the argument list
// TODO (5) From incrementWaterCount, call the PreferenceUtility method that will ultimately update the water count

    companion object {
        val ACTION_INCREMENT_WATER_COUNT = "increment-water-count"
        val ACTION_DISMISS_NOTIFICATION = "dismiss-notification"

        fun executeTask(context: Context, action:String){
            if(ACTION_INCREMENT_WATER_COUNT.equals(action)){
                incrementWaterCount(context)
            }else if(ACTION_DISMISS_NOTIFICATION.equals(action)){
                NotificationUtils.clearAllNotifications(context)
            }else if(Constants.ACTION_CHARGING_REMINDER.equals(action)){
                issueChargingReminder(context)
            }
        }

        private fun issueChargingReminder(context: Context) {
            PreferenceUtilities.incrementChargingReminderCount(context)
            NotificationUtils.remindUserBecauseCharging(context)
        }


        fun incrementWaterCount(context: Context){
            PreferenceUtilities.incrementWaterCount(context)
            NotificationUtils.clearAllNotifications(context)

        }



    }





}