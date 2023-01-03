package com.example.jdm_app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.jdm_app.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val todoViewModel: CarViewModel by viewModels()
        todoViewModel.todoResponse.observe(this) {
            binding.textView.text = todoViewModel.todoResponse.value
        }

        binding.bottomNavigationView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.action_home -> {
                    true
                }
                R.id.action_search -> {
                    true
                }
                R.id.action_settings -> {
                    true
                }
                else -> false
            }
        }
    }
}