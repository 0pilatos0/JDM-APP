package com.example.jdm_app.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.jdm_app.view.CarViewModel
import com.example.jdm_app.R
import com.example.jdm_app.adapter.CarAdapter
import com.example.jdm_app.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    /**
     * Called when the activity is starting.
     * 1. Inflate the ActivityMainBinding layout
     * 2. Set the content view to the root of the binding
     * 3. Set the default selected item for bottomNavigationView
     * 4. Setup the recycler view layout manager
     * 5. observe the carViewModel for updates and use the list for the recyclerView adapter
     * 6. Set up the onClickListener for myCarsButton
     * 7. Set up the onRefreshListener for swipeRefresh
     * 8. Set up the onItemSelectedListener for bottomNavigationView and show/hide the recycler view based on the selected item.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        binding.bottomNavigationView.selectedItemId = R.id.action_cars
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val carViewModel: CarViewModel by viewModels()
        carViewModel.carlist.observe(this) {
            val adapter = CarAdapter(this, it)
            binding.recyclerView.adapter = adapter
        }

        binding.myCarsButton.setOnClickListener {
            val intent = Intent(this, OwnedCarsActivity::class.java)
            startActivity(intent)
        }

        binding.swipeRefresh.setOnRefreshListener {
            carViewModel.getCars()
            binding.swipeRefresh.isRefreshing = false
        }

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