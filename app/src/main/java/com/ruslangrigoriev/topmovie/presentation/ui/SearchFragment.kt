package com.ruslangrigoriev.topmovie.presentation.ui

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.SearchFragmentBinding
import com.ruslangrigoriev.topmovie.presentation.ui.adapters.MoviePagerAdapter
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_ID
import com.ruslangrigoriev.topmovie.domain.utils.QUERY
import com.ruslangrigoriev.topmovie.domain.utils.appComponent
import com.ruslangrigoriev.topmovie.presentation.viewmodel.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchFragment : Fragment() {
    private var _binding: SearchFragmentBinding? = null
    private val binding get() = _binding!!
    @Inject
    lateinit var factory: MyViewModelFactory.Factory
    private lateinit var viewModel: SearchViewModel
    private lateinit var pagerAdapter: MoviePagerAdapter

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = SearchFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val query = arguments?.getString(QUERY)
        if (query != null) {
            viewModel = ViewModelProvider(
                this,
                factory.create(query = query)
            )[SearchViewModel::class.java]
        }

        //set Header
        binding.textViewSearchQuery.text = "for <${query}>"

        //set Recyclerview
        pagerAdapter = MoviePagerAdapter { id -> onListItemClick(id) }
        val gridLM = GridLayoutManager(
            activity,
            2, GridLayoutManager.VERTICAL, false
        )
        binding.recyclerView.layoutManager = gridLM
        binding.recyclerView.adapter = pagerAdapter
        binding.recyclerView.setHasFixedSize(true)
        loadData()

        //search
        binding.searchViewTrending.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    binding.searchViewTrending.clearFocus()
                    if (TextUtils.isEmpty(query)) {
                        Toast.makeText(activity, "Enter your request", Toast.LENGTH_SHORT).show()
                    } else {
                        val bundle = Bundle()
                        bundle.putString(QUERY, query)
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
        lifecycleScope.launch {
            viewModel.searchMoviesFlowData.collectLatest { pagingData ->
                pagerAdapter.submitData(pagingData)
            }
        }
    }

    private fun onListItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putInt(MOVIE_ID, id)
        findNavController().navigate(R.id.action_searchFragment_to_detailsFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}