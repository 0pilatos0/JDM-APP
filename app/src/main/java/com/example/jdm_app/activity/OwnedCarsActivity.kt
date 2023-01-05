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
import com.example.jdm_app.domain.User


class OwnedCarsActivity : AppCompatActivity() {
    private lateinit var binding: CarsOwnedBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = CarsOwnedBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager

        val carViewModel: CarViewModel by viewModels()
        carViewModel.carlist.observe(this) {
            val adapter = OwnedCarAdapter(this, it)

            val recyclerView : RecyclerView = binding.recyclerView
            recyclerView.adapter = adapter
            //TODO REPLACE WITH LOGGED IN USER
        }

        carViewModel.getCarsByUserId(1)


        binding.swipeRefresh.setOnRefreshListener(OnRefreshListener {
            //TODO REPLACE WITH LOGGED IN USER
            carViewModel.getCarsByUserId(1)
            binding.swipeRefresh.isRefreshing = false
        })

        binding.buttonBack.setOnClickListener {
            finish()
        }

        binding.buttonCreate.setOnClickListener {
            var user = User()
            var car = Car()

            user.id = 1
            car.owner = user

            val intent = Intent(this, CarEditActivity::class.java)
            intent.putExtra("car", car)
            this.startActivity(intent)
        }

    }
}