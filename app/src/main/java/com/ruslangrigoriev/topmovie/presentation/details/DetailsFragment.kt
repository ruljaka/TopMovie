package com.ruslangrigoriev.topmovie.presentation.details

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.google.android.material.appbar.AppBarLayout
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.data.api.dto.credits.Cast
import com.ruslangrigoriev.topmovie.databinding.FragmentDetailsBinding
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.domain.utils.ResultState.*
import com.ruslangrigoriev.topmovie.presentation.MainActivity
import com.ruslangrigoriev.topmovie.presentation.adapters.BaseRecyclerAdapter
import com.ruslangrigoriev.topmovie.presentation.adapters.BindingInterface
import com.ruslangrigoriev.topmovie.presentation.video.VideoActivity
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsFragment : Fragment(R.layout.fragment_details) {
    private val binding by viewBinding(FragmentDetailsBinding::bind)
    private val mediaID: Int by intArgs(MEDIA_ID)
    private val mediaType: String by stringArgs(MEDIA_TYPE)
    private val viewModel: DetailsViewModel by viewModels()
    private lateinit var castRecAdapter: BaseRecyclerAdapter<Cast>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbar)
        setHasOptionsMenu(true)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCastRecView()
        setupPlayBtn()
        subscribeUI()
        setBtnFavorite()
        setBtnRate()
        loadData()
    }

    private fun loadData() {
        viewModel.checkIsFavoriteAndRated(mediaID)
        if (viewModel.viewState.value == null
            || viewModel.viewState.value is Failure
        ) {
            viewModel.fetchDetailsData(mediaID, mediaType)
        }
    }

    private fun subscribeUI() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            when (it) {
                Loading -> {
                    showLoading(true)
                }
                is Failure -> {
                    it.errorMessage?.showToast(requireContext())
                    showLoading(false)
                }
                is Success -> {
                    showLoading(false)
                    bindUI(it)
                }
            }
        })
        viewModel.isFavorite.observe(viewLifecycleOwner, {
            when (it) {
                true ->
                    binding.imageButtonDetailsFavorite
                        .setImageResource(R.drawable.ic_favorite_selected)
                false ->
                    binding.imageButtonDetailsFavorite
                        .setImageResource(R.drawable.ic_favorite_unselected)
            }
        })
        viewModel.isRated.observe(viewLifecycleOwner, {
            when (it) {
                true ->
                    binding.imageButtonDetailsLike
                        .setImageResource(R.drawable.ic_like_selected)
                false ->
                    binding.imageButtonDetailsLike
                        .setImageResource(R.drawable.ic_like_unselected)
            }
        })
    }

    private fun bindUI(success: Success) {
        success.listCast?.let {
            castRecAdapter.updateList(it.getTopCast())
        }
        success.details?.let { media ->
            binding.apply {
                toolbar.title = media.title
                textViewDetailsGenre.text = media.genres
                textViewDetailsOverview.text = media.overview
                textViewDetailsVoteCount.text =
                    getString(R.string.details_vote_count, media.voteCount)
                textViewDetailsVoteAverage.text = media.voteAverage.toString()
                media.posterPath?.loadPosterSmall(imageViewDetailsPoster)
                media.backdropPath?.loadBackDropImage(imageViewDetailsBackPoster)
                setupRate(media)

                appbarDetails.addOnOffsetChangedListener(object : AppBarStateChangeListener() {
                    override fun onStateChanged(appBarLayout: AppBarLayout?, state: State?) {
                        when (state) {
                            State.EXPANDED -> {

                            }
                            State.COLLAPSED -> {

                            }
                            State.IDLE -> {

                            }
                        }
                    }

                })
            }
        }
    }

    private fun setupRate(media: Media) {
        val vote = media.voteAverage
        binding.ratingBar.progress = vote.toInt()
    }

    private fun setCastRecView() {
        val bindingInterface = object : BindingInterface<Cast> {
            override fun bindData(item: Cast, view: View) {
                val name: TextView = view.findViewById(R.id.textView_media_cast_name)
                name.text = item.originalName
                val character: TextView = view.findViewById(R.id.textView_media_cast_character)
                character.text = item.character
                val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
                val castPhoto: ImageView = view.findViewById(R.id.imageView_media_cast_poster)
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
            R.layout.item_media_cast,
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

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarDetails.root.visibility = View.VISIBLE
        } else {
            binding.progressBarDetails.root.visibility = View.GONE
        }
    }

    private fun onListItemClick(personID: Int) {
        val bundle = Bundle()
        bundle.putInt(PERSON_ID, personID)
        findNavController().navigate(
            R.id.action_detailsFragment_to_personFragment, bundle
        )
    }

    private fun setBtnFavorite() {
        binding.imageButtonDetailsFavorite.setOnClickListener {
            viewModel.markFavorite(mediaType, mediaID)
        }
    }

    private fun setBtnRate() {
        binding.imageButtonDetailsLike.setOnClickListener {
            setFragmentResultListener(RATE_DIALOG_REQUEST_KEY) { _, bundle ->
                val value = bundle.getFloat(RATE_DIALOG_RESULT_VALUE)
                viewModel.markRated(mediaType, mediaID, value.toString())
            }
            val rateFragment = RateDialogFragment()
            rateFragment.show(parentFragmentManager, RATE_DIALOG_TAG)
        }
    }


    private fun setupPlayBtn() {
        binding.imageButtonDetailsPlay.setOnClickListener {
            activity?.let {
                val intent = Intent(it, VideoActivity::class.java)
                intent.putExtra(MEDIA_ID, mediaID)
                intent.putExtra(MEDIA_TYPE, mediaType)
                it.startActivity(intent)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.share ->
                "share this media".showToast(requireContext())
        }
        return super.onOptionsItemSelected(item)
    }

}