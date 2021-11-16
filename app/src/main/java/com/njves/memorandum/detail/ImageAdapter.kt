package com.njves.memorandum.detail

import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.njves.memorandum.R

class ImageAdapter(private val bitmapList: MutableList<Bitmap>) : RecyclerView.Adapter<ImageAdapter.ImageViewHolder>() {
    fun addItem(bitmap: Bitmap) {
        bitmapList.add(bitmap)
        notifyItemInserted(bitmapList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_image, parent, false)
        return ImageViewHolder(view)
    }

    override fun onBindViewHolder(holder: ImageViewHolder, position: Int) {
        holder.bind(bitmapList[position])
    }

    override fun getItemCount() = bitmapList.size

    inner class ImageViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val ivImage = itemView.findViewById<ImageView>(R.id.iv_image)

        fun bind(bitmap: Bitmap) {
            ivImage.setImageBitmap(bitmap)
        }
    }
}