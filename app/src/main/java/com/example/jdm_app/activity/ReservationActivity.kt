package com.example.jdm_app.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.jdm_app.view.CarViewModel
import com.example.jdm_app.adapter.ReservationAdapter
import com.example.jdm_app.databinding.ReservationsBinding


class ReservationActivity : AppCompatActivity() {
    private lateinit var binding: ReservationsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ReservationsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val carViewModel: CarViewModel by viewModels()
        carViewModel.carlist.observe(this) {
            val adapter = ReservationAdapter(this, it)

            val recyclerView : RecyclerView = binding.recyclerView
            recyclerView.adapter = adapter
            //TODO REPLACE WITH LOGGED IN USER
        }

        carViewModel.getReservationsByUserId(1)


        binding.swipeRefresh.setOnRefreshListener(OnRefreshListener {
            //TODO REPLACE WITH LOGGED IN USER
            carViewModel.getReservationsByUserId(1)
            binding.swipeRefresh.isRefreshing = false
        })

        binding.buttonBack.setOnClickListener {
            finish()
        }
    }
}