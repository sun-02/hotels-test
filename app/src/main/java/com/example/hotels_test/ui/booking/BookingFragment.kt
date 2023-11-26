package com.example.hotels_test.ui.booking

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.content.res.AppCompatResources
import androidx.core.widget.doAfterTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hotels_test.HotelsApp
import com.example.hotels_test.R
import com.example.hotels_test.databinding.FragmentBookingBinding
import com.example.hotels_test.ui.extensions.observe
import com.example.hotels_test.ui.extensions.observeAsEvent
import com.example.hotels_test.ui.order_paid.OrderPaidFragment
import com.google.android.material.textfield.TextInputLayout
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit


class BookingFragment : Fragment() {
    private var _binding: FragmentBookingBinding? = null
    private val binding get() = _binding!!

    private var _adapter: TouristInfoAdapter? = null
    private val adapter get() = _adapter!!

    private val viewModel: BookingViewModel by viewModels {
        BookingViewModel.getFactory(
            (requireActivity().application!! as HotelsApp).repository
        )
    }

    private var phoneNumberHasFocusFirstTime = true
    private val phoneNumberPattern = "+7 (***) ***-**-**"
    private val phoneNumberCursorPositions = listOf<Int>(4, 5, 6, 9, 10, 11, 13, 14, 16, 17)

    private val emailRegex = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}\$".toRegex()

    private val touristStates = mutableListOf<TouristViewHolderState>()
    private lateinit var touristTitles: List<String>

    private var firstBinded = true

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBookingBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        obtainTouristTitles()

        bindViews()
    }

    private fun obtainTouristTitles() {
        val touristTitlesRes = resources.getStringArray(R.array.tourist_array)
        touristTitles = touristTitlesRes.toList()
    }

    private fun bindViews() {
        observe(viewModel.state) { state ->
            if (state == null) return@observe
            with (binding) {
                navigateBack.setOnClickListener {
                    parentFragmentManager.popBackStackImmediate()
                }

                name.text = state.hotelName

                rating.text = "${state.hotelRating} ${state.ratingName}"

                address.text = state.hotelAddress

                departure.text = state.departure
                countryCity.text = state.arrivalCountry
                dates.text = getString(R.string.dates, state.tourDateStart, state.tourDateStop)

                val europeanDatePattern = "dd.MM.yyyy"
                val europeanDateFormatter = DateTimeFormatter.ofPattern(europeanDatePattern)
                val start = LocalDate.parse(state.tourDateStart, europeanDateFormatter)
                val end = LocalDate.parse(state.tourDateStop, europeanDateFormatter)
                val nights = ChronoUnit.DAYS.between(start, end) - 1
                numberOfNights.text = nights.toString()
                hotel.text = state.hotelName
                room.text = state.room
                nutrition.text = state.nutrition

                phoneNumber.setOnFocusChangeListener { _, hasFocus ->
                    if (hasFocus && phoneNumberHasFocusFirstTime) {
                        phoneNumberHasFocusFirstTime = false
                        phoneNumber.setText(phoneNumberPattern)
                        phoneNumber.setSelection(4)
                    } else if (!hasFocus) {
                        setError(phoneNumberLayout, ::validatePhoneNumber)
                    }
                }
                phoneNumber.addTextChangedListener(getPhoneTextWatcher())
                email.setOnFocusChangeListener { _, hasFocus ->
                    if (!hasFocus) {
                        setError(emailLayout, ::validateEmail)
                    }
                }
                email.doAfterTextChanged {
                    setError(emailLayout, ::validateEmail, true)
                }

                if (firstBinded) {
                    firstBinded = false
                    touristStates.add(
                        TouristViewHolderState(touristTitles[0])
                    )
                }
//                touristStates.add(
//                    TouristViewHolderState(
//                        touristTitles[1],
//                        isOpenedAtFirst = false
//                    )
//                )
                tourists.overScrollMode = View.OVER_SCROLL_NEVER
                tourists.isNestedScrollingEnabled = false
                tourists.recycledViewPool.setMaxRecycledViews(0, 0)
                _adapter = TouristInfoAdapter(touristStates, ::addInfo)
                tourists.adapter = adapter
                val dividerDrawable =
                    AppCompatResources.getDrawable(requireContext(), R.drawable.rv_divider_for_vertical)!!
                val itemDivider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
                    .apply { setDrawable(dividerDrawable) }
                tourists.addItemDecoration(itemDivider)

                addTourist.setOnClickListener {
                    if (touristStates.size < 5) {
                        touristStates.add(
                            TouristViewHolderState(
                                touristTitles[touristStates.size],
                                isOpened = false
                            )
                        )
                        adapter.notifyItemInserted(touristStates.size - 1)
                    }
                    if (touristStates.size == 5) {
                        addTourist.isEnabled = false
                    }
                }

                tourValue.text = getString(R.string.price, state.tourPrice)
                fuelFeeValue.text = getString(R.string.price, state.fuelCharge)
                serviceFeeValue.text = getString(R.string.price, state.serviceCharge)
                val checkout = state.tourPrice + state.fuelCharge + state.serviceCharge
                checkoutValue.text = getString(R.string.price, checkout)

                next.text = getString(R.string.checkout_value, checkout)
                next.setOnClickListener {
                    getTouristFields()
                    if (validateAllFields()) {
                        navigateToOrderPaidFragment()
                    } else {
                        setError(binding.phoneNumberLayout, ::validatePhoneNumber)
                        setError(binding.emailLayout, ::validateEmail)
                        showTouristsError()
                    }
                }

            }
        }

        observeAsEvent(viewModel.errorMsg) {
            Toast.makeText(requireContext(), it.value, Toast.LENGTH_LONG).show()
        }
    }

    private fun getTouristFields() {
        touristStates.forEachIndexed { i, state ->
            val holder = binding.tourists.findViewHolderForAdapterPosition(i) as TouristViewHolder
            state.touristInfo = holder.info
        }
    }

    private fun addInfo(info: TouristInfo, i: Int, isOpened: Boolean) {
        touristStates[i].touristInfo = info
        touristStates[i].isOpened = isOpened
    }

    private fun showTouristsError() {
        touristStates.forEachIndexed { i, state ->
            state.showErrors = true
            val holder = binding.tourists.findViewHolderForAdapterPosition(i) as TouristViewHolder
            holder.setError()
        }
    }

    private fun validateAllFields(): Boolean =
        validatePhoneNumber() &&
        validateEmail() &&
        validateTouristFields()

    private fun validateTouristFields(): Boolean =
        touristStates.all {
            it.touristInfo.firstName.isNotBlank() &&
            it.touristInfo.secondName.isNotBlank() &&
            it.touristInfo.dateOfBirth.isNotBlank() &&
            it.touristInfo.citizenship.isNotBlank() &&
            it.touristInfo.passportNumber.isNotBlank() &&
            it.touristInfo.passportValidUntil.isNotBlank()
        }

    private fun setError(v: TextInputLayout, validate: () -> Boolean, setIfNoErrorOnly: Boolean = false) {
        if (validate()) {
            v.setBoxBackgroundColorResource(R.color.fragment_background)
        } else if (!setIfNoErrorOnly){
            v.setBoxBackgroundColorResource(R.color.input_layout_error)
        }
    }

    private fun validatePhoneNumber(): Boolean {
        val digits = binding.phoneNumber.text.toString().toDigits()
        return digits.length == 11 && digits.toCharArray()[1] == '9'
    }
    private fun validateEmail(): Boolean =
        emailRegex.matches(binding.email.text.toString())

    fun String.toDigits() = replace("[^\\d]".toRegex(), "")

    private fun getPhoneTextWatcher() =
        object : TextWatcher {

            private var programmaticallyChanged = true

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}

            override fun afterTextChanged(s: Editable?) {
                if (!programmaticallyChanged) {
                    programmaticallyChanged = true
                    var str = s!!.toString()
                    if (str.length < 2) {
                        str = "7"
                    }
                    var numbers0 = str.toDigits()
                    if (numbers0.length >= 2 && numbers0[1] != '9') {
                        numbers0 = "79" + numbers0.substring(1)
                    }
                    val len = if (numbers0.length <= 11) {
                        numbers0.length
                    } else {
                        11
                    }
                    val numbers = numbers0.substring(1, len).toCharArray()
                    val phoneNumberBuilder = StringBuilder().apply { append(phoneNumberPattern) }
                    for (i in 0 until numbers.size) {
                        val start = phoneNumberCursorPositions[i]
                        val end = phoneNumberCursorPositions[i] + 1
                        phoneNumberBuilder.replace(
                            start,
                            end,
                            numbers[i].toString()
                        )
                    }

                    s?.replace(0, s.length, phoneNumberBuilder.toString())

                    if (numbers.size > 0) {
                        binding.phoneNumber.setSelection(phoneNumberCursorPositions[numbers.size - 1] + 1)
                    } else {
                        binding.phoneNumber.setSelection(phoneNumberCursorPositions[0])
                    }

                    setError(binding.phoneNumberLayout, ::validatePhoneNumber, true)
                } else {
                    programmaticallyChanged = false
                }
            }
        }

    private fun navigateToOrderPaidFragment() {
        parentFragmentManager.commit {
            replace(R.id.fragment_container, OrderPaidFragment::class.java, null)
            setReorderingAllowed(true)
            addToBackStack(null)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }

    companion object {
        @JvmStatic
        fun newInstance() = BookingFragment()
    }
}