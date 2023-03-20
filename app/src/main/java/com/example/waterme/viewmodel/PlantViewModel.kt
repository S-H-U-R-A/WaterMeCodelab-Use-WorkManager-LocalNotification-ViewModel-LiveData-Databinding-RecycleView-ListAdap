package com.example.waterme.viewmodel

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.work.Data
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import com.example.waterme.data.DataSource
import com.example.waterme.worker.WaterReminderWorker
import java.util.concurrent.TimeUnit

class PlantViewModel(
    application: Application
) : ViewModel()
{
    private val workManager: WorkManager = WorkManager.getInstance(application)

    val plants = DataSource.plants

    internal fun scheduleReminder(
        duration: Long,
        unit: TimeUnit,
        plantName: String
    ) {
        // TODO: create a Data instance with the plantName passed to it
        val data: Data = Data.Builder()
            .putString(
                WaterReminderWorker.nameKey,
                plantName
            ).build()

        // TODO: Generate a OneTimeWorkRequest with the passed in duration, time unit, and data
        //  instance
        val waterReminderRequest: OneTimeWorkRequest =
            OneTimeWorkRequest.Builder(WaterReminderWorker::class.java)
                .setInitialDelay(duration, unit)
                .setInputData(data)
                .build()

        // TODO: Enqueue the request as a unique work request
        workManager.enqueueUniqueWork(
            plantName,
            //DETIENE LA TAREA SI ESTA EN EJECUCIÓN
            ExistingWorkPolicy.REPLACE,
            waterReminderRequest
        )

    }
}

class PlantViewModelFactory(private val application: Application) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(PlantViewModel::class.java)) {

            @Suppress("UNCHECKED_CAST")
            PlantViewModel(application) as T

        } else {
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}
