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
        sighInWithLogin("ruljaka", "thLexicon1984")
    }

    private fun sighInWithLogin(username: String, password: String) {
        viewModel.signIn(username, password)
    }

    private fun subscribeUI() {
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner, {
            //TODO
        })
        viewModel.isLogged.observe(viewLifecycleOwner, {
            when (it) {
                true -> {
                    Toast.makeText(
                        requireContext(),
                        "Login successful",
                        Toast.LENGTH_LONG
                    )
                        .show()
                    findNavController().popBackStack()
                }
                false -> {
                    Toast.makeText(
                        requireContext(),
                        "Something went wrong",
                        Toast.LENGTH_LONG
                    )
                        .show()
                }
            }
        })
        viewModel.errorLD.observe(viewLifecycleOwner, {
            //TODO
        })
    }

}