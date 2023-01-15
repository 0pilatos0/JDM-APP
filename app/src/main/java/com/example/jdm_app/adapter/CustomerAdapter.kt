package com.example.jdm_app.adapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jdm_app.activity.CustomerDetailActivity
import com.example.jdm_app.databinding.CustomerItemBinding
import com.example.jdm_app.domain.Customer

class CustomerAdapter(private val context: Context, private val customer: Customer) :
    RecyclerView.Adapter<CustomerViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomerViewHolder {
        val binding: CustomerItemBinding =
            CustomerItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CustomerViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CustomerViewHolder, position: Int) {
        val customer: Customer = customer
        holder.bind(customer)
        holder.itemView.setOnClickListener {
            val intent = Intent(context, CustomerDetailActivity::class.java)
            intent.putExtra("customer", customer)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return 0
    }

}

class CustomerViewHolder(private val binding: CustomerItemBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(customer: Customer) {
        binding.customerUsername.text = customer.username
        binding.customerDateOfBirth.text = customer.dateOfBirth.toString()
        binding.customerAddress.text = customer.address.toString()
        binding.customerPhoneNumber.text = customer.phoneNumber.toString()
    }
}
