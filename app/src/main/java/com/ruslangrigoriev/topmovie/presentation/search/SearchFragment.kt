package com.ruslangrigoriev.topmovie.presentation.search

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
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
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.adapters.MyPagingAdapter
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

class SearchFragment : Fragment(R.layout.fragment_search) {
    private val binding by viewBinding(FragmentSearchBinding::bind)

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: SearchViewModel by viewModels { factory }
    private lateinit var myPagingAdapter: MyPagingAdapter

    private var searchQuery: String? = null
    private var tvQuery: String? = null
    private var mediaType: String? = null

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setMovieRecView()
        setupSearch()
        subscribeQuerySearch()

        searchQuery = arguments?.getString(QUERY)
        mediaType = arguments?.getString(MEDIA_TYPE)

        searchQuery?.let {
            if (viewModel.queryFlow.value == "") {
                viewModel.setQuery(it)
            }
            if(mediaType == MOVIE_TYPE){
                subscribeMovieSearch()
            } else{
                subscribeTvSearch()
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun subscribeMovieSearch() {
        lifecycleScope.launchWhenStarted {
            viewModel.getSearchMoviesFlowData().collectLatest { pagingData ->
                myPagingAdapter.submitData(pagingData)
            }
        }
    }

    @ExperimentalCoroutinesApi
    private fun subscribeTvSearch() {
        lifecycleScope.launchWhenStarted {
            viewModel.searchTvFlowData.collectLatest { pagingData ->
                myPagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun subscribeQuerySearch() {
        lifecycleScope.launchWhenStarted {
            viewModel.queryFlow.collectLatest {
                binding.textViewSearchQuery.text = "for  '${it}'"
            }
        }
    }

    private fun setMovieRecView() {
        myPagingAdapter = MyPagingAdapter { id -> onListItemClick(id) }
        val gridLM = GridLayoutManager(
            activity,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.recyclerView.apply {
            layoutManager = gridLM
            adapter = myPagingAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupSearch() {
        binding.searchViewSearch.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String): Boolean {
                    binding.searchViewSearch.clearFocus()
                    if (TextUtils.isEmpty(query)) {
                        Toast.makeText(activity, "Enter your request", Toast.LENGTH_SHORT).show()
                    } else {
                        viewModel.setQuery(query)
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
    }

    private fun onListItemClick(id: Int) {
            val bundle = Bundle()
            bundle.putInt(MEDIA_ID, id)
            bundle.putString(MEDIA_TYPE, mediaType)
        if(mediaType== MOVIE_TYPE){
            findNavController().navigate(R.id.action_searchFragment_to_details, bundle)
        } else{
            findNavController().navigate(R.id.action_searchTvFragment_to_details, bundle)
        }
    }
}