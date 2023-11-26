package com.example.hotels_test.ui.booking

import androidx.core.view.isVisible
import androidx.core.widget.doAfterTextChanged
import androidx.recyclerview.widget.RecyclerView
import com.example.hotels_test.R
import com.example.hotels_test.databinding.ItemTouristBinding
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout

class TouristViewHolder(
    private val binding: ItemTouristBinding,
    private val addInfo: (TouristInfo, Int, Boolean) -> Unit
) : RecyclerView.ViewHolder(binding.root) {

    private val fields = listOf<TextInputEditText>(
        binding.firstName,
        binding.secondName,
        binding.dateOfBirth,
        binding.citizenship,
        binding.passportNumber,
        binding.passportValidUntil
    )

    private val layouts = listOf<TextInputLayout>(
        binding.firstNameLayout,
        binding.secondNameLayout,
        binding.dateOfBirthLayout,
        binding.citizenshipLayout,
        binding.passportNumberLayout,
        binding.passportValidUntilLayout,
    )
    val info = TouristInfo()

    private var pos: Int = -1

    fun bind(state: TouristViewHolderState, position: Int) {
        pos = position

        binding.title.text = state.title

        fields.forEachIndexed { i, editText ->
            when (i) {
                0 -> editText.setText(state.touristInfo.firstName)
                1 -> editText.setText(state.touristInfo.secondName)
                2 -> editText.setText(state.touristInfo.dateOfBirth)
                3 -> editText.setText(state.touristInfo.citizenship)
                4 -> editText.setText(state.touristInfo.passportNumber)
                5 -> editText.setText(state.touristInfo.passportValidUntil)
            }
            if (state.showErrors && editText.text.toString().isEmpty()) {
                layouts[i].setBoxBackgroundColorResource(R.color.input_layout_error)
            } else {
                layouts[i].setBoxBackgroundColorResource(R.color.fragment_background)
            }
            editText.doAfterTextChanged { text  ->
                when (i) {
                    0 -> info.firstName = text.toString()
                    1 -> info.secondName = text.toString()
                    2 -> info.dateOfBirth = text.toString()
                    3 -> info.citizenship = text.toString()
                    4 -> info.passportNumber = text.toString()
                    5 -> info.passportValidUntil = text.toString()
                }
//                addInfo(info, pos, binding.openCloseButton.isChecked)
                if (text!!.isNotBlank()) {
                    layouts[i].setBoxBackgroundColorResource(R.color.fragment_background)
                }
            }
        }

        binding.openCloseButton.isChecked = state.isOpened
        openCloseContents()
        binding.openCloseButton.setOnClickListener {
            openCloseContents()
        }
    }

    fun setError() {
        fields.forEachIndexed { i, editText ->
            if (editText.text.toString().isEmpty()) {
                layouts[i].setBoxBackgroundColorResource(R.color.input_layout_error)
            } else {
                layouts[i].setBoxBackgroundColorResource(R.color.fragment_background)
            }
        }
    }

    private fun openCloseContents() {
        addInfo(info, pos, binding.openCloseButton.isChecked)
        binding.fieldsBlock.isVisible = binding.openCloseButton.isChecked
    }
}