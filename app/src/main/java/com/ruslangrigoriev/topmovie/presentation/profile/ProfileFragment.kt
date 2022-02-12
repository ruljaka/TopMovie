package com.ruslangrigoriev.topmovie.presentation.profile

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
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
import com.ruslangrigoriev.topmovie.presentation.MainActivity
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentProfileBinding
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.domain.utils.ResultState.*
import com.ruslangrigoriev.topmovie.presentation.adapters.BaseRecyclerAdapter
import com.ruslangrigoriev.topmovie.presentation.adapters.BindingInterface
import com.ruslangrigoriev.topmovie.presentation.adapters.ItemOffsetDecoration
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProfileFragment : Fragment(R.layout.fragment_profile) {
    private val binding by viewBinding(FragmentProfileBinding::bind)
    private val viewModel: ProfileViewModel by viewModels()
    private lateinit var favoriteRecAdapter: BaseRecyclerAdapter<Media>

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbarProfile.toolbar)
        setHasOptionsMenu(true)

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

    private fun bindUI(it: Success) {
        it.user?.let { user ->
            binding.apply {
                if (user.name.isEmpty()) {
                    textViewUsername.text = user.username
                } else {
                    textViewUsername.text = user.name
                }
                if (it.user.avatar.tmdb.avatarPath != null) {
                    Glide.with(requireContext())
                        .load(IMAGE_URL_AVATAR + user.avatar.tmdb.avatarPath)
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .placeholder(R.drawable.placeholder)
                        .into(this.imageViewUserAvatar)
                } else {
                    Glide.with(requireContext())
                        .load(IMAGE_URL_GRAVATAR + user.avatar.gravatar.hash + ".jpg?s=200")
                        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
                        .placeholder(R.drawable.placeholder)
                        .into(this.imageViewUserAvatar)
                }
                textViewProfileLikeCount.text = it.counters?.countLike.toString()
                textViewProfileFavoriteCount.text = it.counters?.countFavorite.toString()
                textViewProfileCommentsCount.text = "0"
            }
        }

        it.favoriteList?.let { list -> favoriteRecAdapter.updateList(list) }
    }

    private fun setFavoriteRecView() {
        val bindingInterface = object : BindingInterface<Media> {
            override fun bindData(item: Media, view: View) {
                val poster = view.findViewById<ImageView>(R.id.imageView_favorite_poster)
                item.posterPath?.loadPosterSmall(poster)
                view.setOnClickListener {
                    onListItemClick(item.id, item.mediaType)
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

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarProfile.visibility = View.VISIBLE
        } else {
            binding.progressBarProfile.visibility = View.GONE
        }
    }


    private fun onListItemClick(id: Int, mediaType: String) {
        val bundle = Bundle()
        bundle.putInt(MEDIA_ID, id)
        bundle.putString(MEDIA_TYPE, mediaType)
        findNavController().navigate(R.id.action_profile_fragment_to_details, bundle)
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.settings -> {
                findNavController().navigate(R.id.action_profile_fragment_to_settingsProfileFragment)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}