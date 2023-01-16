package com.example.jdm_app.adapter

import android.graphics.BitmapFactory
import android.util.Base64
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.jdm_app.databinding.ImageItemBinding

class ImageAdapter(private val images: MutableList<String>) :
    RecyclerView.Adapter<ImageAdapter.ViewHolder>() {

    /**
     * Called when the RecyclerView needs a new ViewHolder to represent an item.
     *
     * @param parent The ViewGroup into which the new View will be added after it is bound to an adapter position.
     * @param viewType The view type of the new View.
     *
     * @return A new ViewHolder that holds a View of the given view type.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ImageItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    /**
     * Called by RecyclerView to display the data at the specified position. This method should update the contents
     * of the ViewHolder to reflect the item at the given position.
     *
     * @param holder The ViewHolder which should be updated to represent the contents of the item at the given position
     * @param position The position of the item within the adapter's data set.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val image = images[position]
        val imageBytes = Base64.decode(image, Base64.DEFAULT)
        val imageBitmap = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.size)
        holder.binding.imageView.setImageBitmap(imageBitmap)
    }

    /**
     * Returns the number of items in the data set held by the adapter.
     *
     * @return The number of items in the adapter's data set.
     */
    override fun getItemCount(): Int {
        return images.size
    }

    /**
     * Clears the list of images.
     */
    fun clear() {
        images.clear()
        notifyDataSetChanged()
    }


    /**
     * Binds the data of the car object to the view.
     * This method is called by the onBindViewHolder to set the car data to the ViewHolder
     *
     * @param binding The binding object that holds the data to be displayed
     */
    class ViewHolder(val binding: ImageItemBinding) : RecyclerView.ViewHolder(binding.root)
}
