package com.example.hotels_test.ui.core

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.example.hotels_test.R

class ViewPagerAdapter(
    private val imageUrls: List<String>
) : RecyclerView.Adapter<ViewPagerAdapter.PagerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PagerViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_view_pager, parent, false)
        return PagerViewHolder(view)
    }

    override fun onBindViewHolder(holder: PagerViewHolder, position: Int) {
        holder.bind(imageUrls[position])
    }

    override fun getItemCount(): Int = imageUrls.size

    class PagerViewHolder(
        private val view: View
    ) : RecyclerView.ViewHolder(view) {
        fun bind(imageUrl: String) {
            val image = view.findViewById<ImageView>(R.id.image)
            image.load(imageUrl)
        }
    }
}