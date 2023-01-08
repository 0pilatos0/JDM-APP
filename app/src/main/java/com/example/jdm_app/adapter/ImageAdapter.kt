package com.example.jdm_app.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jdm_app.databinding.ImageItemBinding

class ImageAdapter(private val images: MutableList<String>) : RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Generate the binding class for the image_item layout
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        val imageBytes = Base64.decode(image, Base64.DEFAULT)
        val imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        holder.binding.imageView.setImageBitmap(imageBitmap)
    }

    override fun getItemCount(): Int {
        return images.size
    }

    class ViewHolder(val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)
}
