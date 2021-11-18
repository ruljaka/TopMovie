package com.ruslangrigoriev.topmovie.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ruslangrigoriev.topmovie.ID
import com.ruslangrigoriev.topmovie.QUERY
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.HomeFragmentBinding
import com.ruslangrigoriev.topmovie.ui.adapters.MoviePagerAdapter
import com.ruslangrigoriev.topmovie.viewmodel.MainViewModel
import com.ruslangrigoriev.topmovie.viewmodel.MyViewModelFactory
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private val viewModel: MainViewModel by viewModels { MyViewModelFactory() }
    private lateinit var pagerAdapter: MoviePagerAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //set pagedRV
        pagerAdapter = MoviePagerAdapter { id -> onListItemClick(id) }
        val gridLM = GridLayoutManager(activity,
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
                        Toast.makeText(activity, "Enter your request",
                            Toast.LENGTH_SHORT).show()
                    } else {
                        val bundle = Bundle()
                        bundle.putString(QUERY, query)
                        binding.searchViewTrending.setQuery("", false)
                        binding.searchViewTrending.isIconified = true
                        findNavController().navigate(
                            R.id.action_trendingFragment_to_searchFragment,
                            bundle
                        )
                    }
                    return false
                }

                override fun onQueryTextChange(newText: String?): Boolean {
                    return false
                }
            })
    }

    private fun loadData() {
        lifecycleScope.launch {
            viewModel.trendingFlowData.collect { pagingData ->
                pagerAdapter.submitData(pagingData)
            }
        }
    }

    private fun onListItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putInt(ID, id)
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}