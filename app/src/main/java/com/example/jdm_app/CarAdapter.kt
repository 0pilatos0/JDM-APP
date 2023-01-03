package com.example.jdm_app

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jdm_app.databinding.CarItemBinding


class CarAdapter(private val mCars: List<Car>) : RecyclerView.Adapter<CarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CarViewHolder {
        val binding: CarItemBinding =
            CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CarViewHolder, position: Int) {
        val car = mCars[position]
        holder.bind(car)
    }

    override fun getItemCount(): Int {
        return mCars.size
    }
}

class CarViewHolder(binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val mBinding: CarItemBinding

    init {
        mBinding = binding
    }

    fun bind(car: Car) {
        mBinding.carType.text = car.carType
        mBinding.carPrice.text = car.price.toString()
        mBinding.carBrand.text = car.brand
    }
}
