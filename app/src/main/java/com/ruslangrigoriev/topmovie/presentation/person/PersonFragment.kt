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
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.adapters.BaseRecyclerAdapter
import com.ruslangrigoriev.topmovie.presentation.adapters.PersonCastAdapter
import com.ruslangrigoriev.topmovie.presentation.person.PersonScreenViewState.*
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

    private fun loadData(personID: Int?) {
        personID?.let {
            if (viewModel.viewState.value == null) {
                viewModel.fetchData(personID)
            }
        }
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
                    bindUI(it)
                }
            }
        })
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarPerson.visibility = View.VISIBLE
        } else {
            binding.progressBarPerson.visibility = View.GONE
        }
    }

    private fun bindUI(it: Success) {
        it.person?.let {
            binding.apply {
                textViewPersonName.text = it.name
                textViewPersonKnownFor.text = it.knownForDepartment
                textViewPersonGender.text = when (it.gender) {
                    1 -> "Female"
                    2 -> "Male"
                    else -> "Undefined"
                }
                textViewPersonBirthday.text = it.birthday
                textViewPersonPlaceOfBirth.text = it.placeOfBirth
                textViewPersonBiography.text = it.biography
                it.profilePath?.loadPosterLarge(imageviewPersonPoster)
            }
        }
        it.personCastList?.let { personCastAdapter.updateList(it.getTopPersonCasts()) }
    }

    private fun setAdapter(view: View) {
        personCastAdapter =
            PersonCastAdapter(emptyList()) { movieID -> onListItemClick(movieID) }
        binding.recyclerViewPerson.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewPerson.adapter = personCastAdapter
    }

    private fun onListItemClick(movieID: Int) {
        val bundle = Bundle()
        bundle.putInt(MEDIA_ID, movieID)
        bundle.putString(SOURCE_TYPE, MOVIE_TYPE)
        findNavController().navigate(R.id.action_personFragment_to_detailsFragment, bundle)
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }
}