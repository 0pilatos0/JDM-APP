package com.example.jdm_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jdm_app.activity.ReservationDetailActivity
import com.example.jdm_app.databinding.CarItemBinding
import com.example.jdm_app.domain.Reservation


class ReservationAdapter(
    private val context: Context, private val mReservations: List<Reservation>
) : RecyclerView.Adapter<ReservationViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReservationViewHolder {
        val binding: CarItemBinding =
            CarItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ReservationViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ReservationViewHolder, position: Int) {
        val reservation: Reservation = mReservations[position]
        holder.bind(reservation)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ReservationDetailActivity::class.java)
            intent.putExtra("reservation", reservation)
            context.startActivity(intent)
        }

    }

    override fun getItemCount(): Int {
        return mReservations.size
    }
}

class ReservationViewHolder(binding: CarItemBinding) : RecyclerView.ViewHolder(binding.root) {
    private val mBinding: CarItemBinding

    init {
        mBinding = binding
    }

    fun bind(reservation: Reservation) {
        mBinding.carType.text = reservation.carListing?.carType
        mBinding.carPrice.text = reservation.carListing?.price.toString()
        mBinding.carBrand.text = reservation.carListing?.brand
    }
}
