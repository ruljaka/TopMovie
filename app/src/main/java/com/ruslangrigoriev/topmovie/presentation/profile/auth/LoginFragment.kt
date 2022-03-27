package com.ruslangrigoriev.topmovie.presentation.profile.auth

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentLoginBinding
import com.ruslangrigoriev.topmovie.domain.utils.ResultState.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)
    private val viewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeUI()
        bindUI()
    }

    private fun sighInWithLogin(username: String, password: String) {
        viewModel.signIn(username, password)
    }

    private fun subscribeUI() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            when (it) {
                Loading -> {
                    binding.progressBarLogin.isVisible = true
                }
                is Failure -> {
                    showToast(it.errorMessage)
                    binding.progressBarLogin.isVisible = false
                }
                is Success -> {
                    binding.progressBarLogin.isVisible = false
                    showToast("Login successful")
                    findNavController().popBackStack()
                }
            }
        })
    }

    private fun bindUI() {
        binding.apply {
            buttonLoginLogin.setOnClickListener {
                val username = editTextLoginUsername.text.toString()
                val password = editTextLoginPassword.text.toString()
                if (username.isNotEmpty() && password.isNotEmpty()
                ) {
                    sighInWithLogin(username, password)
                } else {
                    showToast("Incorrect login or password")
                }
            }
            buttonLoginRegister.setOnClickListener {
                findNavController().navigate(R.id.action_loginFragment_to_registerFragment)
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }
}