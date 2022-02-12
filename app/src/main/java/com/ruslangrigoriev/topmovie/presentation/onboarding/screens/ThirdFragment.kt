package com.ruslangrigoriev.topmovie.presentation.onboarding.screens

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.presentation.MainActivity
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentThirdBinding
import com.ruslangrigoriev.topmovie.domain.utils.onBoardingFinished

class ThirdFragment : Fragment(R.layout.fragment_third) {
    private val binding by viewBinding(FragmentThirdBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.materialButtonThirdScreen.setOnClickListener {
            requireActivity().run {
                startActivity(Intent(this, MainActivity::class.java))
                onBoardingFinished()
                finish()
            }
        }
    }

}