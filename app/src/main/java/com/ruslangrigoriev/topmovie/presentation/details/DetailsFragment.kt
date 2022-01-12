package com.ruslangrigoriev.topmovie.presentation.details

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentDetailsBinding
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.adapters.CastAdapter
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private lateinit var castAdapter: CastAdapter
    private lateinit var movie: Movie

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: DetailsViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val id = arguments?.getInt(MOVIE_ID)

        setAdapter(view)
        setSwitchFavorite(id)
        subscribeUI(view, castAdapter)
        loadData(id)
    }

    private fun setAdapter(view: View) {
        castAdapter = CastAdapter(emptyList()) { personID -> onListItemClick(personID) }
        binding.recyclerViewDetails.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewDetails.adapter = castAdapter
    }

    private fun setSwitchFavorite(id: Int?) {
        //binding.switchFavorite.isChecked = checkFavorites(requireContext().getFavorites(), id!!)
        binding.switchFavorite.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                viewModel.saveToFavorite()
            } else {
                viewModel.deleteFavorite()
            }
        }
    }

    private fun subscribeUI(
        view: View,
        castAdapter: CastAdapter
    ) {
        viewModel.detailsLD.observe(viewLifecycleOwner, {
            binding.textViewDetailsTitle.text = it.title
            binding.textViewDetailsData.text = it.releaseDate.formatDate()
            binding.textViewDetailsRuntime.text = fromMinutesToHHmm(it.runtime)
            binding.textViewDetailsGenre.text = getNamesFromGenre(it.genres)
            binding.textViewDetailsScore.text = ("User Score")
            binding.textViewDetailsOverview.text = it.overview
            val score = it.voteAverage.toString()
                .replace(".", "")
            binding.textViewDetailsProgressbar.text = score
            binding.detailsCircularProgressbar.progress = score.toInt()
            it.posterPath?.loadImageSmall(binding.imageViewDetailsPoster)
            Glide.with(view.context)
                .load(IMAGE_URL + (it.backdropPath))
                .into(binding.imageViewDetailsBackPoster)
        })
        viewModel.castLD.observe(viewLifecycleOwner, {
            castAdapter.updateList(it)
        })
        viewModel.errorLD.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
        viewModel.isLoadingLiveData.observe(viewLifecycleOwner) {
            binding.apply {
                if (it == true) {
                    progressBarDetails.visibility = View.VISIBLE
                } else {
                    progressBarDetails.visibility = View.GONE
                }
            }
        }
    }

    private fun loadData(id: Int?) {
        id?.let {
            if(viewModel.detailsLD.value == null){
                viewModel.getDetails(id)
                viewModel.getCast(id)
            }
        }
    }

    private fun onListItemClick(personID: Int) {
        val bundle = Bundle()
        bundle.putInt(PERSON_ID, personID)
        findNavController().navigate(R.id.action_details_to_personFragment, bundle)
    }
}