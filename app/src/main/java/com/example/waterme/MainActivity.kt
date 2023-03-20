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

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.waterme.adapater.PlantAdapter
import com.example.waterme.adapater.PlantListener
import com.example.waterme.model.Plant
import com.example.waterme.ui.ReminderDialogFragment
import com.example.waterme.viewmodel.PlantViewModel
import com.example.waterme.viewmodel.PlantViewModelFactory

class MainActivity : AppCompatActivity() {

    //SE CREA Y SE OBTIENE EL VIEWMODEL
    private val viewModel: PlantViewModel by viewModels {
        PlantViewModelFactory(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //SE CREA EL ADAPATADOR
        val adapter = PlantAdapter(
            PlantListener { plant ->
                //SE CREA EL DIALOGFRAGMENT
                val dialog = ReminderDialogFragment(plant.name)
                //SE MUESTRA
                dialog.show(
                    supportFragmentManager,
                    "WaterReminderDialogFragment"
                )
                //SE RETORNA EN LA LAMBDA EL BOOLEAN TRUE
                true
            }
        )

        //SE CREA EL RECYCLERVIEW
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        //SE ASIGNA SU ADAPTADOR
        recyclerView.adapter = adapter
        //SE OBTIENE LA LISTA DE PLANTAS
        val data: List<Plant> = viewModel.plants
        //SE AGREGAN AL ADAPTADOR
        adapter.submitList(data)

    }

}
