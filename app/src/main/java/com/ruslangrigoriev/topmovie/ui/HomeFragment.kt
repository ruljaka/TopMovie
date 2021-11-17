package com.ruslangrigoriev.topmovie.ui

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SearchView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.ruslangrigoriev.topmovie.ID
import com.ruslangrigoriev.topmovie.QUERY
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.HomeFragmentBinding
import com.ruslangrigoriev.topmovie.ui.adapters.MovieAdapter
import com.ruslangrigoriev.topmovie.viewmodel.MainViewModel

class HomeFragment : Fragment() {
    private var _binding: HomeFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: MainViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = HomeFragmentBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        //viewModel.getMovies()
        viewModel.getTrendingMovies()

        //set trending in Recyclerview
        val gridLM = GridLayoutManager(
            activity, 2, GridLayoutManager.VERTICAL, false
        )
        binding.recyclerView.layoutManager = gridLM
        val adapter = MovieAdapter({ position -> onListItemClick(position) }, emptyList())
        binding.recyclerView.adapter = adapter
        viewModel.trendingMoviesLD.observe(viewLifecycleOwner, Observer {
            adapter.updateList(it)
        })

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

    private fun onListItemClick(position: Int) {
        val bundle = Bundle()
        viewModel.trendingMoviesLD.value?.get(position)?.let { bundle.putInt(ID, it.id) }
        findNavController().navigate(R.id.action_homeFragment_to_detailsFragment, bundle)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}