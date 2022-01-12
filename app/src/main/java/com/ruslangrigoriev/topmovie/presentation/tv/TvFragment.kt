package com.ruslangrigoriev.topmovie.presentation.tv

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.ruslangrigoriev.topmovie.R

class TvFragment : Fragment() {

    companion object {
        fun newInstance() = TvFragment()
    }

    private lateinit var viewModel: TvViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProvider(this).get(TvViewModel::class.java)
        // TODO: Use the ViewModel
    }

}