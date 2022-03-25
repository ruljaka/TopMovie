package com.ruslangrigoriev.topmovie.presentation.details

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.View
import androidx.core.os.bundleOf
import androidx.fragment.app.DialogFragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentRateDialogBinding
import com.ruslangrigoriev.topmovie.domain.utils.RATE_DIALOG_REQUEST_KEY
import com.ruslangrigoriev.topmovie.domain.utils.RATE_DIALOG_RESULT_VALUE

class RateDialogFragment : DialogFragment(R.layout.fragment_rate_dialog) {
    private val binding by viewBinding(FragmentRateDialogBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.buttonRate.setOnClickListener {
            val selectedValue = binding.ratingbarRateDialog.rating * 2
            parentFragmentManager.setFragmentResult(
                RATE_DIALOG_REQUEST_KEY,
                bundleOf(RATE_DIALOG_RESULT_VALUE to selectedValue)
            )
            dismiss()
        }
        binding.buttonCancel.setOnClickListener {
            dismiss()
        }
    }

    override fun onResume() {
        super.onResume()
        dialog?.window?.setLayout(
            resources.getDimensionPixelSize(
                R.dimen.dialog_width
            ), resources.getDimensionPixelSize(
                R.dimen.dialog_height
            )
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }
}