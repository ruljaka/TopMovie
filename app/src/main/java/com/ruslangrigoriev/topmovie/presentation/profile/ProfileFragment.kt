package com.ruslangrigoriev.topmovie.presentation.profile

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.databinding.FragmentProfileBinding
import com.ruslangrigoriev.topmovie.domain.model.ContentType
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.profile.CounterLikeFavorite
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.adapters.BaseRecyclerAdapter
import com.ruslangrigoriev.topmovie.presentation.adapters.BindingInterface
import com.ruslangrigoriev.topmovie.presentation.adapters.ItemOffsetDecoration
import com.ruslangrigoriev.topmovie.presentation.profile.ProfileScreenViewState.*
import javax.inject.Inject


class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private lateinit var favoriteRecAdapter: BaseRecyclerAdapter<ContentType>

    @Inject
    lateinit var apiService: ApiService

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: ProfileViewModel by viewModels { factory }


    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setFavoriteRecView()
        subscribeUI()


        val isAuth = viewModel.checkIfUserIsAuthenticated()
        if (isAuth) {
            viewModel.fetchUserData()
        } else {
            findNavController().navigate(
                R.id.action_profile_fragment_to_loginProfileFragment
            )
        }

    }

    private fun subscribeUI() {
        lifecycleScope.launchWhenStarted {
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
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarProfile.visibility = View.VISIBLE
        } else {
            binding.progressBarProfile.visibility = View.GONE
        }
    }

    private fun bindUI(it: Success) {
        binding.apply {
            if (it.user.name.isEmpty()) {
                textViewUsername.text = it.user.username
            } else {
                textViewUsername.text = it.user.name
            }
            if (it.user.avatar.tmdb.avatarPath != null) {
                Glide.with(requireContext())
                    .load(IMAGE_URL_AVATAR + it.user.avatar.tmdb.avatarPath)
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .placeholder(R.drawable.placeholder)
                    .into(this.imageViewUserAvatar)
            } else {
                Glide.with(requireContext())
                    .load(IMAGE_URL_GRAVATAR + it.user.avatar.gravatar.hash + ".jpg?s=200")
                    .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                    .placeholder(R.drawable.placeholder)
                    .into(this.imageViewUserAvatar)
            }
            textViewProfileLikeCount.text = it.counters.countLike.toString()
            textViewProfileFavoriteCount.text = it.counters.countFavorite.toString()
        }
        favoriteRecAdapter.updateList(it.favoriteList)
    }

    private fun setFavoriteRecView() {
        val bindingInterface = object : BindingInterface<ContentType> {
            override fun bindData(item: ContentType, view: View) {
                val poster = view.findViewById<ImageView>(R.id.imageView_favorite_poster)
                if (item.getType() == ContentType.TYPE_MOVIE) {
                    (item as Movie).posterPath?.loadPosterSmall(poster)
                    view.setOnClickListener {
                        onListItemClick(item.id, MOVIE_TYPE)
                    }
                } else {
                    (item as TvShow).posterPath?.loadPosterSmall(poster)
                    view.setOnClickListener {
                        onListItemClick(item.id, TV_TYPE)
                    }
                }
            }
        }
        favoriteRecAdapter = BaseRecyclerAdapter(
            emptyList(),
            R.layout.item_favorite,
            bindingInterface,
        )
        binding.apply {
            recyclerViewProfile.layoutManager =
                GridLayoutManager(
                    activity,
                    4,
                    GridLayoutManager.VERTICAL,
                    false
                )
            recyclerViewProfile.adapter = favoriteRecAdapter
            recyclerViewProfile.setHasFixedSize(true)
            recyclerViewProfile.addItemDecoration(
                ItemOffsetDecoration(
                    requireContext(),
                    R.dimen.item_offset
                )
            )
        }
    }

    private fun onListItemClick(id: Int, sourceType: String) {
        val bundle = Bundle()
        bundle.putInt(MEDIA_ID, id)
        bundle.putString(SOURCE_TYPE, sourceType)
        findNavController().navigate(R.id.action_profile_fragment_to_details, bundle)
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }
}