package com.example.hotels_test.ui.booking

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hotels_test.databinding.ItemTouristBinding

class TouristInfoAdapter(
    private val states: List<TouristViewHolderState>,
    private val addInfo: (TouristInfo, Int, Boolean) -> Unit
) : RecyclerView.Adapter<TouristViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TouristViewHolder {
        val binding = ItemTouristBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TouristViewHolder(binding, addInfo)
    }

    override fun onBindViewHolder(holder: TouristViewHolder, position: Int) {
        val item = states[position]
        holder.bind(item, position)
    }

    override fun getItemCount(): Int = states.size
}