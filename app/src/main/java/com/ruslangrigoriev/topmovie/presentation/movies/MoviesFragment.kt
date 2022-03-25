package com.ruslangrigoriev.topmovie.presentation.movies

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentMoviesBinding
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.domain.utils.ResultState.*
import com.ruslangrigoriev.topmovie.presentation.MainActivity
import com.ruslangrigoriev.topmovie.presentation.adapters.MainTabsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {
    private val binding by viewBinding(FragmentMoviesBinding::bind)
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var nowRecyclerAdapter: MainTabsRecyclerAdapter
    private lateinit var popularRecyclerAdapter: MainTabsRecyclerAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbarMovies.toolbar)
        binding.toolbarMovies.toolbarTitle.text = getString(R.string.tab_label_movies)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupSearch()
        subscribeUi()
        loadData()
    }

    private fun loadData() {
        if (viewModel.viewState.value == null
            || viewModel.viewState.value is Failure
        ) {
            viewModel.fetchMoviesData()
        }
    }

    private fun subscribeUi() {
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
    }

    private fun bindUI(it: Success) {
        it.listNow?.let {
            nowRecyclerAdapter =
                MainTabsRecyclerAdapter(it) { id -> onListItemClick(id, MoreType.NOW) }
            binding.recyclerViewNow.layoutManager =
                LinearLayoutManager(
                    activity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            binding.recyclerViewNow.adapter = nowRecyclerAdapter
        }
        it.listPopular?.let {
            popularRecyclerAdapter =
                MainTabsRecyclerAdapter(it) { id -> onListItemClick(id, MoreType.POPULAR) }
            binding.recyclerViewPopular.layoutManager =
                GridLayoutManager(
                    activity,
                    2,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
            binding.recyclerViewPopular.adapter = popularRecyclerAdapter
            binding.recyclerViewPopular.setHasFixedSize(true)
        }
    }

    private fun setupSearch() {
        binding.toolbarMovies.searchView.visibility = View.VISIBLE
        binding.toolbarMovies.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (TextUtils.isEmpty(query)) {
                    getString(R.string.enter_your_request).showToast(requireContext())
                } else {
                    val bundle = Bundle()
                    bundle.putString(QUERY, query)
                    bundle.putString(MEDIA_TYPE, MOVIE_TYPE)
                    findNavController().navigate(
                        R.id.action_movies_fragment_to_searchFragment, bundle
                    )
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarMovies.root.visibility = View.VISIBLE
        } else {
            binding.progressBarMovies.root.visibility = View.GONE
        }
    }

    private fun onListItemClick(id: Int, moreType: MoreType) {
        if (id == 0) {
            val bundle = Bundle()
            bundle.putString(MEDIA_TYPE, MOVIE_TYPE)
            bundle.putString(MORE_TYPE, moreType.name)
            findNavController().navigate(R.id.action_movies_fragment_to_more, bundle)
        } else {
            val bundle = Bundle()
            bundle.putInt(MEDIA_ID, id)
            bundle.putString(MEDIA_TYPE, MOVIE_TYPE)
            findNavController().navigate(R.id.action_movies_fragment_to_details, bundle)
        }
    }

}