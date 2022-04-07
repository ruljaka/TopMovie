package com.ruslangrigoriev.topmovie.presentation.tv

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentTvBinding
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.domain.utils.ResultState.*
import com.ruslangrigoriev.topmovie.presentation.MainActivity
import com.ruslangrigoriev.topmovie.presentation.adapters.MainTabsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvFragment : Fragment(R.layout.fragment_tv) {
    private val binding by viewBinding(FragmentTvBinding::bind)
    private val viewModel: TvViewModel by viewModels()
    private lateinit var topRecyclerAdapter: MainTabsRecyclerAdapter
    private lateinit var popularRecyclerAdapter: MainTabsRecyclerAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbarTv.toolbar)
        binding.toolbarTv.toolbarTitle.text = "TV"
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
            viewModel.fetchData()
        }
    }

    private fun subscribeUi() {
        viewModel.viewState.observe(viewLifecycleOwner, {
            when (it) {
                Loading -> {
                    binding.progressBarTv.root.isVisible = true
                }
                is Failure -> {
                    showToast(it.errorMessage)
                    binding.progressBarTv.root.isVisible = false
                }
                is Success -> {
                    binding.progressBarTv.root.isVisible = false
                    bindUI(it)
                }
            }
        })
    }

    private fun bindUI(it: Success) {
        it.listTop?.let {
            topRecyclerAdapter =
                MainTabsRecyclerAdapter(it) { id -> onListItemClick(id, MoreType.TOP) }
            binding.recyclerViewTvTop.apply {
                layoutManager =
                    LinearLayoutManager(
                        activity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                adapter = topRecyclerAdapter
            }
        }
        it.listPopular?.let {
            popularRecyclerAdapter =
                MainTabsRecyclerAdapter(it) { id -> onListItemClick(id, MoreType.POPULAR) }
            binding.recyclerViewTvPopular.apply {
                layoutManager =
                    GridLayoutManager(
                        activity,
                        2,
                        GridLayoutManager.HORIZONTAL,
                        false
                    )
                adapter = popularRecyclerAdapter
                setHasFixedSize(true)
            }
        }
    }

    private fun setupSearch() {
        binding.toolbarTv.searchView.visibility = View.VISIBLE
        binding.toolbarTv.searchView.setOnQueryTextListener(
            object : SearchView.OnQueryTextListener,
                OnQueryTextListener {
                override fun onQueryTextSubmit(query: String?): Boolean {
                    if (TextUtils.isEmpty(query)) {
                        showToast("Enter your request")
                    } else {
                        val bundle = Bundle()
                        bundle.putString(QUERY, query)
                        bundle.putString(MEDIA_TYPE, TV_TYPE)
                        findNavController().navigate(
                            R.id.action_tv_fragment_to_searchTvFragment,
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

    private fun onListItemClick(id: Int, moreType: MoreType) {
        if (id == 0) {
            val bundle = Bundle()
            bundle.putString(MEDIA_TYPE, TV_TYPE)
            bundle.putString(MORE_TYPE, moreType.name)
            findNavController().navigate(R.id.action_tv_fragment_to_more, bundle)
        } else {
            val bundle = Bundle()
            bundle.putInt(MEDIA_ID, id)
            bundle.putString(MEDIA_TYPE, TV_TYPE)
            findNavController().navigate(R.id.action_tv_fragment_to_details, bundle)
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }

}