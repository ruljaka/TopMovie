package com.ruslangrigoriev.topmovie.presentation.movies

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentMoviesBinding
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.domain.utils.ResultState.*
import com.ruslangrigoriev.topmovie.presentation.MainActivity
import com.ruslangrigoriev.topmovie.presentation.adapters.BaseRecyclerAdapter
import com.ruslangrigoriev.topmovie.presentation.adapters.BindingInterface
import com.ruslangrigoriev.topmovie.presentation.adapters.MediaPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoviesFragment : Fragment(R.layout.fragment_movies) {
    private val binding by viewBinding(FragmentMoviesBinding::bind)
    private val viewModel: MovieViewModel by viewModels()
    private lateinit var pagingAdapter: MediaPagingAdapter
    private lateinit var nowRecyclerAdapter: BaseRecyclerAdapter<Media>
    private lateinit var popularRecyclerAdapter: BaseRecyclerAdapter<Media>

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbarMovies.toolbar)
        binding.toolbarMovies.toolbarTitle.text = "MOVIES"
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setNowRecView()
        setPopularRecView()
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
        it.listNow?.let { now -> nowRecyclerAdapter.updateList(now) }
        it.listPopular?.let { popular -> popularRecyclerAdapter.updateList(popular) }
    }

    private fun setNowRecView() {
        val bindingInterface = object : BindingInterface<Media> {
            override fun bindData(item: Media, view: View) {
                val title: TextView = view.findViewById(R.id.textView_now_title)
                title.text = item.title
                val poster: ImageView = view.findViewById(R.id.imageView_now_poster)
                item.posterPath?.loadPosterLarge(poster)
                view.setOnClickListener {
                    onListItemClick(item.id)
                }
            }
        }
        nowRecyclerAdapter =
            BaseRecyclerAdapter(
                emptyList(),
                R.layout.item_movie_now,
                bindingInterface
            )
        binding.recyclerViewNow.layoutManager =
            LinearLayoutManager(
                activity,
                LinearLayoutManager.HORIZONTAL,
                false
            )
        binding.recyclerViewNow.adapter = nowRecyclerAdapter
    }

    private fun setPopularRecView() {
        val bindingInterface = object : BindingInterface<Media> {
            override fun bindData(item: Media, view: View) {
                val title: TextView = view.findViewById(R.id.textView_popular_title)
                title.text = item.title
                val date: TextView = view.findViewById(R.id.textView_popular_date)
                date.text = item.releaseDate?.formatDate()
                val vote: TextView = view.findViewById(R.id.textView_popular_score)
                vote.text = item.voteAverage.toString()
                val poster: ImageView = view.findViewById(R.id.imageView_popular_poster)
                item.posterPath?.loadPosterLarge(poster)
                view.setOnClickListener {
                    onListItemClick(item.id)
                }
            }
        }
        popularRecyclerAdapter = BaseRecyclerAdapter(
            emptyList(),
            R.layout.item_movie_popular,
            bindingInterface,
        )
        binding.apply {
            recyclerViewPopular.layoutManager =
                GridLayoutManager(
                    activity,
                    2,
                    GridLayoutManager.HORIZONTAL,
                    false
                )
            recyclerViewPopular.adapter = popularRecyclerAdapter
            recyclerViewPopular.setHasFixedSize(true)
        }

    }

    private fun setupSearch() {
        binding.toolbarMovies.searchView.visibility = View.VISIBLE
        binding.toolbarMovies.searchView.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (TextUtils.isEmpty(query)) {
                    showToast("Enter your request")
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
            binding.progressBarMovies.visibility = View.VISIBLE
        } else {
            binding.progressBarMovies.visibility = View.GONE
        }
    }

    private fun onListItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putInt(MEDIA_ID, id)
        bundle.putString(MEDIA_TYPE, MOVIE_TYPE)
        findNavController().navigate(R.id.action_movies_fragment_to_details, bundle)
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }


    /*private fun setPagedRecView(view: View) {
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
}*/

    /*private fun subscribeUI() {
        lifecycleScope.launchWhenStarted {
            viewModel.trendingFlowData.collect { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }*/


}