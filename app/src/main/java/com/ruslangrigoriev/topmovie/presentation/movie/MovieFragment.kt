package com.ruslangrigoriev.topmovie.presentation.movie

import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentMoviesBinding
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_ID
import com.ruslangrigoriev.topmovie.domain.utils.appComponent
import com.ruslangrigoriev.topmovie.domain.utils.getFavorites
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.adapters.MoviePagingAdapter
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

class MovieFragment : Fragment(R.layout.fragment_movies) {
    private val binding by viewBinding(FragmentMoviesBinding::bind)

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: MovieViewModel by viewModels { factory }
    private lateinit var pagingAdapter: MoviePagingAdapter

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setPagedRecView(view)
        subscribeUI()

    }

    private fun setPagedRecView(view: View) {
        val favoriteList = view.context.getFavorites()
        pagingAdapter = MoviePagingAdapter({ id -> onListItemClick(id) }, favoriteList)
        val gridLM = GridLayoutManager(
            activity,
            2, GridLayoutManager.VERTICAL, false
        )
        binding.recyclerView.layoutManager = gridLM
        binding.recyclerView.adapter = pagingAdapter
        binding.recyclerView.setHasFixedSize(true)

        pagingAdapter.addLoadStateListener {
            if (it.refresh is LoadState.Error) {
                showToast((it.refresh as? LoadState.Error)?.error?.message)
            }
        }
    }

    private fun subscribeUI() {
        lifecycleScope.launchWhenStarted {
            viewModel.trendingFlowData.collect { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun onListItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, id)
        findNavController().navigate(R.id.action_movie_to_detailsFragment, bundle)
    }

    private fun showToast(error: String?) {
        Toast.makeText(
            activity, error ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }

}