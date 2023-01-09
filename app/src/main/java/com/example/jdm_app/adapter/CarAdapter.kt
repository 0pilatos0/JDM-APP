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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding: CarItemBinding =
            CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car: Car = mCars[position]
        holder.bind(car)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CarDetailActivity::class.java)
            intent.putExtra("car", car)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int = mCars.size
}

class CarViewHolder(private val binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(car: Car) {
        binding.carType.text = car.carType
        binding.carPrice.text = car.price.toString()
        binding.carBrand.text = car.brand
    }
}
