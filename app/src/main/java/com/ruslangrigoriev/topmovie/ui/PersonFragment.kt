package com.ruslangrigoriev.topmovie.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.PersonFragmentBinding
import com.ruslangrigoriev.topmovie.ui.adapters.PersonCastAdapter
import com.ruslangrigoriev.topmovie.utils.MOVIE_ID
import com.ruslangrigoriev.topmovie.utils.PERSON_ID
import com.ruslangrigoriev.topmovie.utils.getTopPersonCasts
import com.ruslangrigoriev.topmovie.utils.loadImageLarge
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
            viewModel.getPersonCredits(personID)
        }

        //set castAdapter
        val personCastAdapter =
            PersonCastAdapter(emptyList()) { movieID -> onListItemClick(movieID) }
        binding.recyclerViewPerson.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewPerson.adapter = personCastAdapter

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
        viewModel.personCastLD.observe(viewLifecycleOwner, {
            personCastAdapter.updateList(it.cast.getTopPersonCasts())
        })
        viewModel.errorLD.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    private fun onListItemClick(movieID: Int) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, movieID)
        findNavController().navigate(R.id.action_personFragment_to_detailsFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}