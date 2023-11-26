package com.example.hotels_test.ui.room

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotels_test.HotelsApp
import com.example.hotels_test.R
import com.example.hotels_test.databinding.FragmentRoomsBinding
import com.example.hotels_test.ui.booking.BookingFragment
import com.example.hotels_test.ui.extensions.observe
import com.example.hotels_test.ui.extensions.observeAsEvent
import com.example.hotels_test.ui.hotel.HotelFragment
import com.google.android.material.tabs.TabLayoutMediator

class RoomsFragment : Fragment() {
    private var _binding: FragmentRoomsBinding? = null
    private val binding get() = _binding!!

    private var _adapter: RoomsAdapter? = null
    private val adapter get() = _adapter!!

    private var hotelName: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            hotelName = it.getString(HOTEL_NAME)
        }
    }

    private val viewModel: RoomsViewModel by viewModels {
        RoomsViewModel.getFactory(
            (requireActivity().application!! as HotelsApp).repository
        )
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentRoomsBinding.inflate(inflater, container, false)
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
                navigateBack.setOnClickListener {
                    parentFragmentManager.popBackStackImmediate()
                }

                _adapter = RoomsAdapter(::navigateToBookingFragment)
                rooms.adapter = adapter
                val dividerDrawable =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.rv_divider_for_vertical)!!
                val itemDivider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                    .apply { setDrawable(dividerDrawable) }
                rooms.addItemDecoration(itemDivider)
                adapter.submitList(state.rooms)
            }
        }

        observeAsEvent(viewModel.errorMsg) {
            Toast.makeText(requireContext(), it.value, Toast.LENGTH_LONG).show()
        }
    }

    private fun navigateToBookingFragment() {
        parentFragmentManager.commit {
            val fragment = BookingFragment.newInstance()
            replace(R.id.fragment_container, fragment)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
        _adapter = null
    }

    companion object {
        private const val HOTEL_NAME = "HotelName"

        @JvmStatic
        fun newInstance(hotelName: String) =
            RoomsFragment().apply {
                arguments = Bundle().apply {
                    putString(HOTEL_NAME, hotelName)
                }
            }
    }
}