package com.ruslangrigoriev.topmovie.presentation.onboarding

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.ruslangrigoriev.topmovie.MainActivity
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.domain.utils.onBoardingFinished
import com.ruslangrigoriev.topmovie.domain.utils.onBoardingIsFinished


class SplashFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        Handler().postDelayed({
            if (requireContext().onBoardingIsFinished()) {
                requireActivity().run {
                    startActivity(Intent(this, MainActivity::class.java))
                    onBoardingFinished()
                    finish()
                }
            } else {
                findNavController().navigate(R.id.action_splashFragment_to_viewPagerFragment)
            }
        }, 1000)
        return inflater.inflate(R.layout.fragment_splash, container, false)
    }


}