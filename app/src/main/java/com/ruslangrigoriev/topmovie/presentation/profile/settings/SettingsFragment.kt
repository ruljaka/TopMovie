package com.ruslangrigoriev.topmovie.presentation.profile.settings

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentSettingsBinding
import com.ruslangrigoriev.topmovie.domain.utils.appComponent
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.profile.login.LoginViewModel
import javax.inject.Inject

class SettingsFragment : Fragment(R.layout.fragment_settings) {
    val binding by viewBinding(FragmentSettingsBinding::bind)

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: SettingsViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSettingsLogout.setOnClickListener {
            viewModel.authRepository.logout()
            showToast("Logged out")
            findNavController().popBackStack()
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }

}