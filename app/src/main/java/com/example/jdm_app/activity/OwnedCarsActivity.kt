package com.example.jdm_app.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout.OnRefreshListener
import com.example.jdm_app.view.CarViewModel
import com.example.jdm_app.adapter.OwnedCarAdapter
import com.example.jdm_app.databinding.CarsOwnedBinding
import com.example.jdm_app.domain.Car
import com.example.jdm_app.domain.Customer


class OwnedCarsActivity : AppCompatActivity() {
    private lateinit var binding: CarsOwnedBinding

    /**
     * Called when the activity is starting.
     * 1. Inflate the `CarsOwnedBinding` layout.
     * 2. Set the content view to the root of the binding.
     * 3. Setups up the recyclerView by creating LinearLayoutManager and adding the adapter to the recyclerView.
     * 4. Observes the carlist in the CarViewModel and binds it to the adapter of the recyclerView
     * 5. When the swipeRefresh is triggered, the cars are fetched by userId.
     * 6. The back button is setup to close the current activity when clicked.
     * 7. The create button is setup to start the CarEditActivity and pass a new car object to it with owner set as the logged in user.
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CarsOwnedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val carViewModel: CarViewModel by viewModels()
        carViewModel.carlist.observe(this) {
            val adapter = OwnedCarAdapter(this, it)

            val recyclerView: RecyclerView = binding.recyclerView
            recyclerView.adapter = adapter
        }

        carViewModel.getCarsByUserId(MainActivity.customer?.id!!)


        binding.swipeRefresh.setOnRefreshListener(OnRefreshListener {
            carViewModel.getCarsByUserId(MainActivity.customer?.id!!)
            binding.swipeRefresh.isRefreshing = false
        })

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.buttonCreate.setOnClickListener {
            var user = Customer()
            var car = Car()

            user.id = MainActivity.customer?.id
            car.owner = user

            val intent = Intent(this, CarEditActivity::class.java)
            intent.putExtra("car", car)
            this.startActivity(intent)
        }

    }
}