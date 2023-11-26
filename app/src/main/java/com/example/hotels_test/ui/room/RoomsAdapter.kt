package com.example.hotels_test.ui.room

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.hotels_test.databinding.ItemRoomBinding
import com.example.hotels_test.domain.room.Room

class RoomsAdapter(
    private val navigate: () -> Unit
) : ListAdapter<Room, RoomsViewHolder>(DiffCallback) {

    private lateinit var binding: ItemRoomBinding

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomsViewHolder {
        binding = ItemRoomBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RoomsViewHolder(binding, navigate)
    }

    override fun onBindViewHolder(holder: RoomsViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<Room>() {
        override fun areItemsTheSame(oldItem: Room, newItem: Room): Boolean =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Room, newItem: Room): Boolean =
            oldItem == newItem
    }
}