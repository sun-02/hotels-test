package com.example.hotels_test.ui.room

import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.hotels_test.R
import com.example.hotels_test.databinding.ItemRoomBinding
import com.example.hotels_test.domain.room.Room
import com.example.hotels_test.ui.core.ViewPagerAdapter
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator

class RoomsViewHolder(
    private val binding: ItemRoomBinding,
    private val navigate: () -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private var firstCreated = true

    fun bind(room: Room) {
        with (binding) {
            pager.adapter = ViewPagerAdapter(room.imageUrls)

            TabLayoutMediator(pagerDots, pager) { _, _ -> }.attach()
            
            roomName.text = room.name

            val first = room.price / 1000
            val second = room.price % 1000
            val priceFormatted = "$first $second"
            price.text = root.context.getString(R.string.price_from, priceFormatted)

            priceForIt.text = room.pricePer.lowercase()

            if (firstCreated) {
                val dividerDrawable =
                    AppCompatResources.getDrawable(root.context, R.drawable.rv_divider_for_horizontal)!!
                val itemDivider = DividerItemDecoration(root.context, LinearLayoutManager.HORIZONTAL)
                    .apply { setDrawable(dividerDrawable) }
                pager.addItemDecoration(itemDivider)
                room.peculiarities.forEach {
                    val chip = Chip(root.context).apply {
                        text = it
                        setEnsureMinTouchTargetSize(false)
                        isEnabled = false
                        chipStrokeColor = ContextCompat.getColorStateList(root.context, R.color.chip_back)
                        setChipBackgroundColorResource(R.color.chip_back)
                        setTextAppearance(R.style.TextAppearance_MaterialComponents_Chip)
                    }
                    peculiarities.addView(chip)
                }
                firstCreated = false
            }

            next.setOnClickListener {
                firstCreated = true
                navigate()
            }
        }
    }
}