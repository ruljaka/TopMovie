package com.ruslangrigoriev.topmovie.presentation.onboarding

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentViewPagerBinding
import com.ruslangrigoriev.topmovie.presentation.onboarding.screens.FirstFragment
import com.ruslangrigoriev.topmovie.presentation.onboarding.screens.SecondFragment
import com.ruslangrigoriev.topmovie.presentation.onboarding.screens.ThirdFragment

class ViewPagerFragment : Fragment(R.layout.fragment_view_pager) {
    private val binding by viewBinding(FragmentViewPagerBinding::bind)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val fragmentList = arrayListOf(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )
        val adapter = ViewPagerAdapter(
            fragmentList,
            requireActivity().supportFragmentManager,
            lifecycle
        )
        binding.viewPager.adapter = adapter
    }

}