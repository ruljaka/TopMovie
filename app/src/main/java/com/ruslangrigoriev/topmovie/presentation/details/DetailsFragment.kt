package com.ruslangrigoriev.topmovie.presentation.details

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
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentDetailsBinding
import com.ruslangrigoriev.topmovie.domain.model.credits.Cast
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.adapters.BaseRecyclerAdapter
import com.ruslangrigoriev.topmovie.presentation.adapters.BindingInterface
import javax.inject.Inject

class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private lateinit var castRecAdapter: BaseRecyclerAdapter<Cast>
    private var movieID = 0
    private var tvID = 0

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: DetailsViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setCastRecView()
        setupBackButton()
        subscribeUI()
        loadData()

    }

    private fun setCastRecView() {
        val bindingInterface = object : BindingInterface<Cast> {
            override fun bindData(item: Cast, view: View) {
                val name: TextView = view.findViewById(R.id.textView_cast_name)
                name.text = item.originalName
                val character: TextView = view.findViewById(R.id.textView_cast_character)
                character.text = item.character
                val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                val castPhoto: ImageView = view.findViewById(R.id.imageView_cast)
                Glide.with(requireContext())
                    .load(IMAGE_URL_W500 + (item.profilePath))
                    .apply(requestOptions)
                    .thumbnail(0.1f)
                    .apply(RequestOptions().override(300, 450))
                    .placeholder(R.drawable.placeholder)
                    .into(castPhoto)
                view.setOnClickListener {
                    onListItemClick(item.id)
                }
            }
        }
        castRecAdapter = BaseRecyclerAdapter(
            emptyList(),
            R.layout.item_cast,
            bindingInterface
        )
        binding.recyclerViewDetails.apply {
            layoutManager = LinearLayoutManager(
                requireContext(),
                LinearLayoutManager.HORIZONTAL,
                false
            )
            adapter = castRecAdapter
            setHasFixedSize(true)
        }
    }

    private fun subscribeUI() {
        viewModel.movieDetailsLD.observe(viewLifecycleOwner, {
            binding.apply {
                textViewDetailsTitle.text = it.title
                textViewDetailsGenre.text = getNamesFromGenre(it.genres)
                textViewDetailsOverview.text = it.overview
                textViewDetailsVoteCount.text = "${it.voteCount}  People watched"
                textViewDetailsVoteAverage.text = it.voteAverage.toString()
                it.posterPath?.loadPosterSmall(imageViewDetailsPoster)
                it.backdropPath?.loadBackDropImage(imageViewDetailsBackPoster)
                    ?: it.posterPath?.loadPosterLarge(imageViewDetailsBackPoster)
            }
        })

        viewModel.tvDetailsLD.observe(viewLifecycleOwner, {
            binding.apply {
                textViewDetailsTitle.text = it.originalName
                textViewDetailsGenre.text = getNamesFromGenre(it.genres)
                textViewDetailsOverview.text = it.overview
                textViewDetailsVoteCount.text = "${it.voteCount}  People watched"
                textViewDetailsVoteAverage.text = it.voteAverage.toString()
                it.posterPath?.loadPosterSmall(imageViewDetailsPoster)
                it.backdropPath?.loadBackDropImage(imageViewDetailsBackPoster)
            }
        })

        viewModel.movieCastLD.observe(viewLifecycleOwner, {
            castRecAdapter.updateList(it.getTopCast())
        })
        viewModel.tvCastLD.observe(viewLifecycleOwner, {
            castRecAdapter.updateList(it.getTopCast())
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

    private fun loadData() {
        movieID = arguments?.getInt(MOVIE_ID) ?: 0
        tvID = arguments?.getInt(TV_ID) ?: 0
        if (movieID != 0) {
            if (viewModel.movieDetailsLD.value == null) {
                viewModel.fetchMovieDetailsData(movieID)
            }
        }
        if (tvID != 0) {
            if (viewModel.tvDetailsLD.value == null) {
                viewModel.fetchTvDetailsData(tvID)
            }
        }
    }

    private fun onListItemClick(personID: Int) {
        val bundle = Bundle()
        bundle.putInt(PERSON_ID, personID)
        if (movieID != 0) {
            findNavController().navigate(R.id.action_details_to_personFragment, bundle)
        }
        if (tvID != 0) {
            findNavController().navigate(R.id.action_detailsTvFragment_to_personTvFragment, bundle)
        }

    }

    private fun setupBackButton() {
        binding.buttonDetailsBack.setOnClickListener {
            findNavController().popBackStack()
        }
    }
}