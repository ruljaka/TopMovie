package com.ruslangrigoriev.topmovie.presentation.details

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.google.android.material.appbar.AppBarLayout
import com.ruslangrigoriev.topmovie.MainActivity
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentDetailsNewBinding
import com.ruslangrigoriev.topmovie.domain.model.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.domain.utils.ResultState.*
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.adapters.BaseRecyclerAdapter
import com.ruslangrigoriev.topmovie.presentation.adapters.BindingInterface
import com.ruslangrigoriev.topmovie.presentation.video.VideoActivity
import javax.inject.Inject


class DetailsFragment : Fragment(R.layout.fragment_details_new) {
    private val binding by viewBinding(FragmentDetailsNewBinding::bind)
    private val mediaID: Int by intArgs(MEDIA_ID)
    private val mediaType: String by stringArgs(MEDIA_TYPE)

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: DetailsViewModel by viewModels { factory }
    private lateinit var castRecAdapter: BaseRecyclerAdapter<Cast>

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbar)
        setHasOptionsMenu(true)

        setCastRecView()
        setupPlayBtn()
        subscribeUI()
        setBtnFavorite()
        loadData()
    }

    private fun loadData() {
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

    private fun bindUI(success: Success) {
        success.listCast?.let {
            castRecAdapter.updateList(it.getTopCast())
        }
        success.details?.let { media ->
            binding.apply {
                toolbar.title = media.title
                textViewDetailsGenre.text = media.genres?.let { getNamesFromGenre(it) }
                textViewDetailsOverview.text = media.overview
                textViewDetailsVoteCount.text = "${media.voteCount}  People watched"
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
        if (vote > 0.0) {
            binding.detailsRate1.setImageResource(R.drawable.ic_star_red)
        }
        if (vote > 3.0) {
            binding.detailsRate2.setImageResource(R.drawable.ic_star_red)
        }
        if (vote > 6.0) {
            binding.detailsRate3.setImageResource(R.drawable.ic_star_red)
        }
        if (vote > 7.0) {
            binding.detailsRate4.setImageResource(R.drawable.ic_star_red)
        }
        if (vote > 9.0) {
            binding.detailsRate5.setImageResource(R.drawable.ic_star_red)
        }
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

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarDetails.visibility = View.VISIBLE
        } else {
            binding.progressBarDetails.visibility = View.GONE
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
                showToast("share this media")
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }
}