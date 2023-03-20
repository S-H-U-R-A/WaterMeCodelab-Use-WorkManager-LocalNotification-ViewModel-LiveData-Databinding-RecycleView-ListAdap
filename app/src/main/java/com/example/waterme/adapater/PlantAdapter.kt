package com.example.waterme.adapater

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.waterme.databinding.ListItemBinding
import com.example.waterme.model.Plant

class PlantAdapter(
    private val longClickListener: PlantListener
    ) : ListAdapter<Plant, PlantAdapter.PlantViewHolder>(DiffCallback)
{

    class PlantViewHolder(
        private val binding: ListItemBinding
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            longClickListener: PlantListener,
            plant: Plant
        ) {
            binding.plant = plant
            binding.longClickListner = longClickListener
            //ACTUALICE LOS BINDINGS
            binding.executePendingBindings()
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantViewHolder {
        //SE OBTIENE EL INFLATER
        val layoutInflater = LayoutInflater.from(parent.context)
        //SE CREA LA VISTA Y SE INFLA
        return PlantViewHolder(
            ListItemBinding.inflate(layoutInflater, parent, false)
        )
    }

    override fun onBindViewHolder(holder: PlantViewHolder, position: Int) {
        //OBTENEMOS LA POSICIÓN EN LA LISTA
        val plant = getItem(position)
        //SE LLAMA AL MÉTODO BIND Y SE LE PASA EL LISTENER Y EL ITEM
        holder.bind(longClickListener, plant)
    }

    companion object DiffCallback: DiffUtil.ItemCallback<Plant>() {
        override fun areItemsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Plant, newItem: Plant): Boolean {
            return oldItem.name == newItem.name
        }

    }

}

//CLASS LISTENER
class PlantListener(
    val longClickListener: (plant: Plant) -> Boolean
) {
    fun onLongClick(plant: Plant) = longClickListener(plant)
}
