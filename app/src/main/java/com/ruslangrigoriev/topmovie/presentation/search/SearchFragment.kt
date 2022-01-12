package com.ruslangrigoriev.topmovie.presentation.search

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentSearchBinding
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_ID
import com.ruslangrigoriev.topmovie.domain.utils.TAG
import com.ruslangrigoriev.topmovie.domain.utils.appComponent
import com.ruslangrigoriev.topmovie.domain.utils.getFavorites
import com.ruslangrigoriev.topmovie.presentation.adapters.MoviePagingAdapter
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: SearchViewModel by viewModels { factory }
    private lateinit var pagingAdapter: MoviePagingAdapter

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setRecView(view)
        setupSearch()
        loadData()

    }

    private fun setRecView(view: View) {
        val favoriteList = view.context.getFavorites()
        pagingAdapter = MoviePagingAdapter({ id -> onListItemClick(id) }, favoriteList)
        val gridLM = GridLayoutManager(
            activity,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.layoutManager = gridLM
        binding.recyclerView.adapter = pagingAdapter
        binding.recyclerView.setHasFixedSize(true)
    }

    private fun setupSearch() {
        binding.searchViewTrending.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchViewTrending.clearFocus()
                    if (TextUtils.isEmpty(query)) {
                        Toast.makeText(activity, "Enter your request", Toast.LENGTH_SHORT).show()
                    } else {
                        binding.textViewSearchQuery.text = "for <${query}>"
                        viewModel.queryFlow.value = query!!
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }

            })
    }

    @ExperimentalCoroutinesApi
    private fun loadData() {
        lifecycleScope.launchWhenStarted {
            viewModel.searchMoviesFlowData.collectLatest { pagingData ->
                if (viewModel.queryFlow.value != "") {
                    pagingAdapter.submitData(pagingData)
                    binding.textViewSearchQuery.text = "for <${viewModel.queryFlow.value}>"
                }

            }
        }
        Log.d(TAG, "SearchFragment :: loadData")
    }

    private fun onListItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, id)
        //findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
    }
}