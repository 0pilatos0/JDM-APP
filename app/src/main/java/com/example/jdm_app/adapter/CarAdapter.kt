package com.example.jdm_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jdm_app.activity.CarDetailActivity
import com.example.jdm_app.databinding.CarItemBinding
import com.example.jdm_app.domain.Car

class CarAdapter(private val context: Context, private val mCars: List<Car>) :
    RecyclerView.Adapter<CarViewHolder>() {
    /**
     * Called when the RecyclerView needs a new ViewHolder to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new CarViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding: CarItemBinding =
            CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should update the contents
     * of the ViewHolder to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position
     * @param position The position of the item within the adapter's data set.
     */

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car: Car = mCars[position]
        holder.bind(car)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CarDetailActivity::class.java)
            intent.putExtra("car", car)
            context.startActivity(intent)
        }
    }

    /**
     * Returns the number of items in the data set held by the adapter.
     *
     * @return The number of items in the adapter's data set.
     */
    override fun getItemCount(): Int = mCars.size
}

class CarViewHolder(private val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root) {
    /**
     * Binds the data of the car object to the view.
     * This method is called by the onBindViewHolder to set the car data to the ViewHolder
     *
     * @param car The car object that holds the data to be displayed
     */
    fun bind(car: Car) {
        binding.carType.text = car.carType
        binding.carPrice.text = car.price.toString()
        binding.carBrand.text = car.brand
    }
}
