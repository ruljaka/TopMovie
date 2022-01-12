package com.ruslangrigoriev.topmovie.presentation.onboarding.screens

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentSecondBinding


class SecondFragment : Fragment(R.layout.fragment_second) {
    val binding by viewBinding(FragmentSecondBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.materialButtonSecondScreen.setOnClickListener {
            val viewPager = activity?.findViewById<ViewPager2>(R.id.view_pager)
            viewPager?.currentItem = 2
        }
    }
}