package com.thinkdevs.hyndrate.sync


import android.app.IntentService
import android.content.Intent

/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

// TODO (9) Create WaterReminderIntentService and extend it from IntentService
class WaterReminderIntentService :IntentService("WaterReminderIntentService"){

    override fun onHandleIntent(intent: Intent?) {

        val action = intent!!.getAction()

//      COMPLETED (13) Call ReminderTasks.executeTask and pass in the action to be performed
        ReminderTasks.executeTask(this, action)

    }
}

