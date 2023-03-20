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
package com.example.waterme.worker

import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.waterme.BaseApplication
import com.example.waterme.MainActivity
import com.example.waterme.R

class WaterReminderWorker(
    context: Context,
    workerParams: WorkerParameters
) : Worker(
    context,
    workerParams
) {

    // Arbitrary id number
    val notificationId = 17

    override fun doWork(): Result {

        val intent = Intent(
            applicationContext,
            MainActivity::class.java
        ).apply {
            flags =
                //ACÁ SE INDICA QUE LA ACTIVIDAD PASADA PARA ESTE CASO MainActivity
                //ES UNA NUEVA TAREA DE LA APLICACIÓN
                Intent.FLAG_ACTIVITY_NEW_TASK or
                //INDICA QUE CUANDO SE EJECUTE ESTE INTENT TODAS LAS ACTIVIDADES
                //EXISTENTES DEBEN ELIMINARSE CONVIRTIENDO ESTA NUEVA EN LA
                //UNICA EN LA PILA
                Intent.FLAG_ACTIVITY_CLEAR_TASK
        }

        //ESTE OBJETO EJECUTA EL INTENT CUANDO LO USEN A EL O USANDO
        //EL MÉTODO SEND DE EL MISMO OBJETO
        val pendingIntent: PendingIntent =
            PendingIntent.getActivity(
                applicationContext,
                0,
                intent, //<-Intent envuelto
                0
            )

        //SE RECUPERA EL NOMBRE DE LA PLANTA
        val plantName = inputData.getString(nameKey)

        //SE CONSTRUYE LA NOTIFICACIÓN
        val builder = NotificationCompat.Builder(
            applicationContext,
            BaseApplication.CHANNEL_ID
        )
            .setSmallIcon(R.drawable.ic_android_black_24dp)
            .setContentTitle("Water me!")
            .setContentText("It's time to water your $plantName")
            .setPriority( NotificationCompat.PRIORITY_HIGH )
            .setContentIntent( pendingIntent )
            .setAutoCancel(true)

        with(
            NotificationManagerCompat.from( applicationContext )
        ) {
            //SE ENVIA LA NOTIFICACIÓN
            notify(notificationId, builder.build())
        }
        //SE RETONA EL WORKER COMO EXITOSO
        return Result.success()
    }

    companion object {
        //NOMBRE DE LA CLAVE DEL MAPA PARA RECUPERAR EL VALOR ENVIADO AL WORKER
        const val nameKey = "NAME"
    }
}
