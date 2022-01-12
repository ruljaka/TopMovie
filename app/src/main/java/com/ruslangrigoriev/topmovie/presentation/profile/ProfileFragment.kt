package com.ruslangrigoriev.topmovie.presentation.profile

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentProfileBinding
import com.ruslangrigoriev.topmovie.domain.model.favorite.Favorite
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import com.ruslangrigoriev.topmovie.domain.utils.appComponent
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import javax.inject.Inject

class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: ProfileViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        //viewModel.getFavoriteList()
        viewModel.favoriteLD.observe(viewLifecycleOwner, {
            Log.d(
                TAG,
                "FavoriteFragment -> observe ${it.map(Favorite::originalTitle)}"
            )
        })

    }

}