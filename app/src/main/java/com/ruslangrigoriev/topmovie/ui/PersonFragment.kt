package com.ruslangrigoriev.topmovie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.ruslangrigoriev.topmovie.PERSON_ID
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.PersonFragmentBinding
import com.ruslangrigoriev.topmovie.loadImageLarge
import com.ruslangrigoriev.topmovie.viewmodel.MyViewModelFactory
import com.ruslangrigoriev.topmovie.viewmodel.PersonViewModel


class PersonFragment : Fragment() {
    private var _binding: PersonFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: PersonViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = PersonFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get Data
        val personID = arguments?.getInt(PERSON_ID)
        personID?.let {
            viewModel =
                ViewModelProvider(
                    this,
                    MyViewModelFactory()
                )[PersonViewModel::class.java]
            viewModel.getPerson(personID)
        }

        //update UI
        viewModel.personLD.observe(viewLifecycleOwner, {
            binding.textViewPersonName.text = it.name
            binding.textViewPersonKnownFor.text = it.knownForDepartment
            binding.textViewPersonGender.text = when (it.gender) {
                1 -> "Female"
                2 -> "Male"
                else -> "Undefined"
            }
            binding.textViewPersonBirthday.text = it.birthday
            binding.textViewPersonPlaceOfBirth.text = it.placeOfBirth
            binding.textViewPersonBiography.text = it.biography
            it.profilePath?.loadImageLarge(binding.imageviewPersonPoster)

        })

    }
}