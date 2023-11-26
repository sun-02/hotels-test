package com.example.hotels_test.ui.order_paid

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import com.example.hotels_test.HotelsApp
import com.example.hotels_test.R
import com.example.hotels_test.databinding.FragmentBookingBinding
import com.example.hotels_test.databinding.FragmentOrderPaidBinding
import com.example.hotels_test.ui.booking.BookingViewModel
import com.example.hotels_test.ui.booking.TouristInfoAdapter
import com.example.hotels_test.ui.booking.TouristViewHolderState
import com.example.hotels_test.ui.hotel.HotelFragment
import kotlin.random.Random
import kotlin.random.nextInt

class OrderPaidFragment : Fragment() {

    private var _binding: FragmentOrderPaidBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentOrderPaidBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        bindViews()
    }

    private fun bindViews() {
        binding.navigateBack.setOnClickListener {
            parentFragmentManager.popBackStackImmediate()
        }

        val orderNumber = Random.nextInt(100000..999999)
        binding.orderDetail.text = getString(R.string.order_detail, orderNumber)

        binding.next.setOnClickListener {
            popToHotelFragment()
        }
    }

    private fun popToHotelFragment() {
        parentFragmentManager.popBackStack(HotelFragment.transactionTag, FragmentManager.POP_BACK_STACK_INCLUSIVE)
    }

    companion object {
        @JvmStatic
        fun newInstance() = OrderPaidFragment()
    }
}