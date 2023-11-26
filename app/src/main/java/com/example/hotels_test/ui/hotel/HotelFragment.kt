package com.example.hotels_test.ui.hotel

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotels_test.HotelsApp
import com.example.hotels_test.R
import com.example.hotels_test.databinding.FragmentHotelBinding
import com.example.hotels_test.ui.core.ViewPagerAdapter
import com.example.hotels_test.ui.extensions.observe
import com.example.hotels_test.ui.extensions.observeAsEvent
import com.example.hotels_test.ui.room.RoomsFragment
import com.google.android.material.chip.Chip
import com.google.android.material.tabs.TabLayoutMediator


class HotelFragment : Fragment() {

    private var _binding: FragmentHotelBinding? = null
    private val binding get() = _binding!!

    private var _adapter: ViewPagerAdapter? = null
    private val adapter get() = _adapter!!

    private val viewModel: HotelViewModel by viewModels {
        HotelViewModel.getFactory(
            (requireActivity().application!! as HotelsApp).repository
        )
    }

    private var firstCreated = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHotelBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
    }

    private fun bindViews() {
        observe(viewModel.state) { state ->
            if (state == null) return@observe
            with (binding) {
                _adapter = ViewPagerAdapter(state.imageUrls)
                pager.adapter = adapter

                TabLayoutMediator(pagerDots, pager) { _, _ -> }.attach()

                name.text = state.name

                rating.text = "${state.rating} ${state.ratingName}"

                address.text = state.address

                val first = state.minimalPrice / 1000
                val second = state.minimalPrice % 1000
                val priceFormatted = "$first $second"
                price.text = getString(R.string.price_from, priceFormatted)

                priceForIt.text = state.priceForIt.lowercase()

                if (firstCreated) {
                    val dividerDrawable =
                        AppCompatResources.getDrawable(requireContext(), R.drawable.rv_divider_for_horizontal)!!
                    val itemDivider = DividerItemDecoration(context, LinearLayoutManager.HORIZONTAL)
                        .apply { setDrawable(dividerDrawable) }
                    pager.addItemDecoration(itemDivider)
                    state.peculiarities.forEach {
                        val chip = Chip(requireContext()).apply {
                            text = it
                            setEnsureMinTouchTargetSize(false)
                            isEnabled = false
                            chipStrokeColor = ContextCompat.getColorStateList(requireContext(), R.color.chip_back)
                            setChipBackgroundColorResource(R.color.chip_back)
                            setTextAppearance(R.style.TextAppearance_MaterialComponents_Chip)
                        }
                        peculiarities.addView(chip)
                    }
                    firstCreated = false
                }

                description.text = state.description

                next.setOnClickListener {
                    firstCreated = true
                    navigateToRoomsFragment(state.name)
                }
            }
        }

        observeAsEvent(viewModel.errorMsg) {
            Toast.makeText(requireContext(), it.value, Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToRoomsFragment(hotelName: String) {
        parentFragmentManager.commit {
            val fragment = RoomsFragment.newInstance(hotelName)
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
            addToBackStack(transactionTag)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        _adapter = null
    }

    companion object {
        const val transactionTag = "HotelFragment"
        fun newInstance() = HotelFragment()
    }
}