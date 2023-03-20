/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.waterme

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build

class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        //SI LA VERSIÓN DEL SDK DE COMPILACIÓN ES MAYOR A OREO
        //SE CREA EL CANAL PARA LA NOTIFICACIÓN
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {

            //SE CONFIGURA Y CREA EL CANAL
            val name = getString(R.string.channel_name)
            val descriptionText = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(
                CHANNEL_ID,
                name,
                importance
            ).apply {
                description = descriptionText
            }

            //SE OBTIENE EL MANEJADOR DE NOTIFICACIONES
            val notificationManager: NotificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

            //SE AGREGA EL CANAL EN EL SISTEMA OPERATIVO
            notificationManager.createNotificationChannel(
                channel
            )
        }

    }

    companion object {
        //ID DEL CANAL
        const val CHANNEL_ID = "water_reminder_id"
    }
}
