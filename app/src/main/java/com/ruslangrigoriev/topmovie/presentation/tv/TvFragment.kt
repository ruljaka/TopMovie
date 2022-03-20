package com.ruslangrigoriev.topmovie.presentation.tv

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.SearchView
import android.widget.Toast
import androidx.appcompat.widget.SearchView.OnQueryTextListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentTvBinding
import com.ruslangrigoriev.topmovie.domain.utils.MEDIA_ID
import com.ruslangrigoriev.topmovie.domain.utils.MEDIA_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.QUERY
import com.ruslangrigoriev.topmovie.domain.utils.ResultState.*
import com.ruslangrigoriev.topmovie.domain.utils.TV_TYPE
import com.ruslangrigoriev.topmovie.presentation.MainActivity
import com.ruslangrigoriev.topmovie.presentation.adapters.MainTabsRecyclerAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TvFragment : Fragment(R.layout.fragment_tv) {
    private val binding by viewBinding(FragmentTvBinding::bind)
    private val viewModel: TvViewModel by viewModels()
    private lateinit var nowRecyclerAdapter: MainTabsRecyclerAdapter
    private lateinit var popularRecyclerAdapter: MainTabsRecyclerAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbarTv.toolbar)
        binding.toolbarTv.toolbarTitle.text = "TV"
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
        it.listNow?.let {
            nowRecyclerAdapter = MainTabsRecyclerAdapter(it) { id -> onListItemClick(id) }
            binding.recyclerViewTvNow.apply {
                layoutManager =
                    LinearLayoutManager(
                        activity,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                adapter = nowRecyclerAdapter
            }
        }
        it.listPopular?.let {
            popularRecyclerAdapter = MainTabsRecyclerAdapter(it) { id -> onListItemClick(id) }
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

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarTv.visibility = View.VISIBLE
        } else {
            binding.progressBarTv.visibility = View.GONE
        }
    }

    private fun onListItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putInt(MEDIA_ID, id)
        bundle.putString(MEDIA_TYPE, TV_TYPE)
        findNavController().navigate(R.id.action_tv_fragment_to_details, bundle)
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            activity, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }

}