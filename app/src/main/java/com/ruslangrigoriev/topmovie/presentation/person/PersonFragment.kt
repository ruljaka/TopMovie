package com.ruslangrigoriev.topmovie.presentation.person

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentPersonBinding
import com.ruslangrigoriev.topmovie.domain.model.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.person.Person
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.adapters.BaseRecyclerAdapter
import com.ruslangrigoriev.topmovie.presentation.adapters.PersonCastAdapter
import javax.inject.Inject


class PersonFragment : Fragment(R.layout.fragment_person) {
    private val binding by viewBinding(FragmentPersonBinding::bind)

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: PersonViewModel by viewModels { factory }

    private lateinit var personCastAdapter: PersonCastAdapter
    private lateinit var personRecAdapter: BaseRecyclerAdapter<Cast>

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val personID = arguments?.getInt(PERSON_ID)
        loadData(personID)
        setAdapter(view)
        subscribeUI()
    }

    private fun subscribeUI() {
        viewModel.personLD.observe(viewLifecycleOwner, {
            bindUI(it)
        })
        viewModel.personCastLD.observe(viewLifecycleOwner, {
            personCastAdapter.updateList(it.cast.getTopPersonCasts())
        })
        viewModel.errorLD.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            binding.apply {
                if (it == true) {
                    progressBarPerson.visibility = View.VISIBLE
                } else progressBarPerson.visibility = View.GONE
            }
        }
    }

    private fun bindUI(it: Person) {
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
        it.profilePath?.loadPosterLarge(binding.imageviewPersonPoster)
    }

    private fun setAdapter(view: View) {
        personCastAdapter =
            PersonCastAdapter(emptyList()) { movieID -> onListItemClick(movieID) }
        binding.recyclerViewPerson.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewPerson.adapter = personCastAdapter


    }

    private fun loadData(personID: Int?) {
        personID?.let {
            if (viewModel.personLD.value == null) {
                viewModel.fetchData(personID)
            }
        }
    }

    private fun onListItemClick(movieID: Int) {
        //TODO
//        val bundle = Bundle()
//        bundle.putInt(MOVIE_ID, movieID)
//        findNavController().navigate(R.id.action_person_to_details, bundle)
    }
}