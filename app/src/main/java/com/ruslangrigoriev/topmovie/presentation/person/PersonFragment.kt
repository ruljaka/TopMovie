package com.ruslangrigoriev.topmovie.presentation.person

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.MainActivity
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentPersonBinding
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.adapters.BaseRecyclerAdapter
import com.ruslangrigoriev.topmovie.presentation.adapters.BindingInterface
import com.ruslangrigoriev.topmovie.presentation.person.PersonScreenViewState.*
import javax.inject.Inject


class PersonFragment : Fragment(R.layout.fragment_person) {
    private val binding by viewBinding(FragmentPersonBinding::bind)

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: PersonViewModel by viewModels { factory }

    private lateinit var castRecAdapter: BaseRecyclerAdapter<Media>

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbarPerson.toolbar)
        (requireActivity() as MainActivity).supportActionBar?.setDisplayShowTitleEnabled(false)

        val personID = arguments?.getInt(PERSON_ID)
        loadData(personID)
        setCastRecView()
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
        castRecAdapter.updateList(it.personCastList.getTopPersonCasts())
    }

    private fun setCastRecView() {
        val bindingInterface = object : BindingInterface<Media> {
            override fun bindData(item: Media, view: View) {
                val title: TextView = view.findViewById(R.id.textView_person_cast_movie_name)
                title.text = item.originalTitle
                val poster: ImageView = view.findViewById(R.id.imageView_person_cast_poster)
                item.posterPath?.loadPosterSmall(poster)
                view.setOnClickListener {
                    onListItemClick(item.id)
                }
            }
        }
        castRecAdapter =
            BaseRecyclerAdapter(
                emptyList(),
                R.layout.item_person_cast,
                bindingInterface
            )
        binding.recyclerViewPerson.layoutManager =
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        binding.recyclerViewPerson.adapter = castRecAdapter
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarPerson.visibility = View.VISIBLE
        } else {
            binding.progressBarPerson.visibility = View.GONE
        }
    }

    private fun onListItemClick(movieID: Int) {
        val bundle = Bundle()
        bundle.putInt(MEDIA_ID, movieID)
        bundle.putString(MEDIA_TYPE, MOVIE_TYPE)
        findNavController().navigate(R.id.action_personFragment_to_detailsFragment, bundle)
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }
}