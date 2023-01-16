package com.example.jdm_app.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.jdm_app.R
import com.example.jdm_app.adapter.ReservationAdapter
import com.example.jdm_app.databinding.ReservationsBinding
import com.example.jdm_app.view.ReservationViewModel


class ReservationActivity : AppCompatActivity() {
    private lateinit var binding: ReservationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReservationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val reservationViewModel: ReservationViewModel by viewModels()
        reservationViewModel.reservationlist.observe(this) {
            val adapter = ReservationAdapter(this, it)
            binding.recyclerView.adapter = adapter
        }

        reservationViewModel.getReservationsByUserId(MainActivity.customer?.id!!)

        binding.swipeRefresh.setOnRefreshListener {
            reservationViewModel.getReservationsByUserId(MainActivity.customer?.id!!)
            binding.swipeRefresh.isRefreshing = false
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_reservation -> {
                    val intent = Intent(this, ReservationActivity::class.java)
                    this.startActivity(intent)
                    true
                }
                R.id.action_cars -> {
                    val intent = Intent(this, MainActivity::class.java)
                    this.startActivity(intent)
                    true
                }
                R.id.action_profile -> {
                    val intent = Intent(this, CustomerDetailActivity::class.java)
                    this.startActivity(intent)
                    true
                }
                else -> false
            }
        }
    }
}