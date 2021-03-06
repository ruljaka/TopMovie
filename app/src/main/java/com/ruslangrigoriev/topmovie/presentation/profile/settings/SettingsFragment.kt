package com.ruslangrigoriev.topmovie.presentation.profile.settings

import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentSettingsBinding
import com.ruslangrigoriev.topmovie.domain.utils.extensions.changeColorMode
import com.ruslangrigoriev.topmovie.domain.utils.extensions.getColorMode
import com.ruslangrigoriev.topmovie.domain.utils.extensions.saveColorMode
import com.ruslangrigoriev.topmovie.presentation.MainActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SettingsFragment : Fragment(R.layout.fragment_settings) {
    val binding by viewBinding(FragmentSettingsBinding::bind)
    private val viewModel: SettingsViewModel by viewModels()

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbarSettings.toolbar)
        binding.toolbarSettings.toolbarTitle.text = "Settings"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.buttonSettingsLogout.setOnClickListener {
            viewModel.logout()
            showToast("Logged out")
            findNavController().popBackStack()
        }
    }

    override fun onResume() {
        super.onResume()
        setupDarkModeSettings()
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }

    private fun setupDarkModeSettings() {
        val colorModeList = resources.getStringArray(R.array.dark_mode_entries)
        val savedColorMode = requireContext().getColorMode()
        val adapter = ArrayAdapter(requireContext(), R.layout.item_dropdown, colorModeList)
        binding.autocompleteTextviewDarkMode.setText(colorModeList[savedColorMode])
        binding.autocompleteTextviewDarkMode.setAdapter(adapter)
        binding.autocompleteTextviewDarkMode.setOnItemClickListener { _, _, position, _ ->
            requireContext().saveColorMode(position)
            changeColorMode(position)
        }
    }
}