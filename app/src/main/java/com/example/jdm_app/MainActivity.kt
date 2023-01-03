package com.example.jdm_app

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.jdm_app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.bottomNavigationView.selectedItemId = R.id.action_cars
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val carViewModel: CarViewModel by viewModels()
        carViewModel.carlist.observe(this) {
            val adapter = CarAdapter(it)

            val recyclerView : RecyclerView = binding.recyclerView
            recyclerView.adapter = adapter
        }

        binding.swipeRefresh.setOnRefreshListener(OnRefreshListener {
            carViewModel.getCars()
            binding.swipeRefresh.isRefreshing = false
        })

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_reservation -> {
                    binding.recyclerView.visibility = RecyclerView.GONE
                    true
                }
                R.id.action_cars -> {
                    binding.recyclerView.visibility = RecyclerView.VISIBLE
                    true

                }
                R.id.action_profile -> {
                    binding.recyclerView.visibility = RecyclerView.GONE
                    true
                }
                else -> false
            }
        }
    }
}