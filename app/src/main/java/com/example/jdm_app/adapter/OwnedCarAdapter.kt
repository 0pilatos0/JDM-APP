package com.example.jdm_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jdm_app.activity.CarEditActivity
import com.example.jdm_app.databinding.CarItemBinding
import com.example.jdm_app.domain.Car


class OwnedCarAdapter(private val context: Context, private val mCars: List<Car>) : RecyclerView.Adapter<OwnedCarViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OwnedCarViewHolder {
        val binding: CarItemBinding =
            CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OwnedCarViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OwnedCarViewHolder, position: Int) {
        val car: Car = mCars[position]
        holder.bind(car)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CarEditActivity::class.java)
            intent.putExtra("car", car)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mCars.size
    }
}

class OwnedCarViewHolder(binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root) {
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
