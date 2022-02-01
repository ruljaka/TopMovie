package com.ruslangrigoriev.topmovie.presentation.profile.login

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentLoginBinding
import com.ruslangrigoriev.topmovie.domain.utils.ResultState
import com.ruslangrigoriev.topmovie.domain.utils.ResultState.*
import com.ruslangrigoriev.topmovie.domain.utils.appComponent
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import javax.inject.Inject

class LoginFragment : Fragment(R.layout.fragment_login) {
    private val binding by viewBinding(FragmentLoginBinding::bind)

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: LoginViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

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
                    showLoading(true)
                }
                is Failure -> {
                    showToast(it.errorMessage)
                    showLoading(false)
                }
                is Success -> {
                    showLoading(false)
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
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarLogin.visibility = View.VISIBLE
        } else {
            binding.progressBarLogin.visibility = View.GONE
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }
}