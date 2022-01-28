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
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ruslangrigoriev.topmovie.MainActivity
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentDetailsBinding
import com.ruslangrigoriev.topmovie.databinding.FragmentDetailsNewBinding
import com.ruslangrigoriev.topmovie.domain.model.credits.Cast
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.adapters.BaseRecyclerAdapter
import com.ruslangrigoriev.topmovie.presentation.adapters.BindingInterface
import com.ruslangrigoriev.topmovie.presentation.details.DetailsScreenViewState.*
import com.ruslangrigoriev.topmovie.presentation.video.VideoActivity
import javax.inject.Inject


class DetailsFragment : Fragment(R.layout.fragment_details_new) {
    private val binding by viewBinding(FragmentDetailsNewBinding::bind)
    private lateinit var castRecAdapter: BaseRecyclerAdapter<Cast>


    private var mediaID = 0
    private var sourceType: String? = null

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: DetailsViewModel by viewModels { factory }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (requireActivity() as MainActivity).setupToolbar(binding.toolbar)
        setHasOptionsMenu(true)


        setCastRecView()
        //setupBackButton()
        setupPlayBtn()
        subscribeUI()
        loadData()
    }

    private fun loadData() {
        mediaID = arguments?.getInt(MEDIA_ID) ?: 0
        sourceType = arguments?.getString(SOURCE_TYPE)
        if ((sourceType != null) && (viewModel.viewState.value == null)) {
            viewModel.fetchDetailsData(mediaID, sourceType!!)
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
                is SuccessMovie -> {
                    showLoading(false)
                    bindUI(it)
                }
                is SuccessTvShow -> {
                    showLoading(false)
                    bindUI(it)
                }
            }
        })
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarDetails.visibility = View.VISIBLE
        } else {
            binding.progressBarDetails.visibility = View.GONE
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

    private fun bindUI(successTvShow: SuccessTvShow) {
        successTvShow.details?.let { tvShow ->
            binding.apply {
                //textViewDetailsTitle.text = tvShow.originalName
                textViewDetailsGenre.text = getNamesFromGenre(tvShow.genres)
                textViewDetailsOverview.text = tvShow.overview
                textViewDetailsVoteCount.text = "${tvShow.voteCount}  People watched"
                textViewDetailsVoteAverage.text = tvShow.voteAverage.toString()
                tvShow.posterPath?.loadPosterSmall(imageViewDetailsPoster)
                tvShow.backdropPath?.loadBackDropImage(imageViewDetailsBackPoster)
                toolbar.title = tvShow.originalName
            }
        }
        successTvShow.listCast?.let {
            castRecAdapter.updateList(it.getTopCast())
        }
    }

    private fun bindUI(successMovie: SuccessMovie) {
        successMovie.details?.let { movie ->
            binding.apply {
               // textViewDetailsTitle.text = movie.title
                textViewDetailsGenre.text = getNamesFromGenre(movie.genres)
                textViewDetailsOverview.text = movie.overview
                textViewDetailsVoteCount.text = "${movie.voteCount}  People watched"
                textViewDetailsVoteAverage.text = movie.voteAverage.toString()
                movie.posterPath?.loadPosterSmall(imageViewDetailsPoster)
                movie.backdropPath?.loadBackDropImage(imageViewDetailsBackPoster)
                    ?: movie.posterPath?.loadPosterLarge(imageViewDetailsBackPoster)
                toolbar.title = movie.title
            }
        }
        successMovie.listCast?.let {
            castRecAdapter.updateList(it.getTopCast())
        }
    }

    private fun onListItemClick(personID: Int) {
        val bundle = Bundle()
        bundle.putInt(PERSON_ID, personID)
        findNavController().navigate(
            R.id.action_detailsFragment_to_personFragment, bundle
        )
    }

    private fun setupPlayBtn() {
        binding.imageButtonDetailsPlay.setOnClickListener {
            activity?.let {
                val intent = Intent(it, VideoActivity::class.java)
                intent.putExtra(MEDIA_ID, mediaID)
                intent.putExtra(SOURCE_TYPE, sourceType)
                it.startActivity(intent)
            }
        }
    }

//    private fun setupBackButton() {
//        binding.buttonDetailsBack.setOnClickListener {
//            findNavController().popBackStack()
//        }
//    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_details, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        showToast("CLICK")
        return super.onOptionsItemSelected(item)
    }

}