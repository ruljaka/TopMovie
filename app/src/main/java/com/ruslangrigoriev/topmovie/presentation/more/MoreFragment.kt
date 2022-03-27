package com.ruslangrigoriev.topmovie.presentation.more

import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.LoadState
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentMoreBinding
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.domain.utils.extensions.showToast
import com.ruslangrigoriev.topmovie.domain.utils.extensions.stringArgs
import com.ruslangrigoriev.topmovie.presentation.MainActivity
import com.ruslangrigoriev.topmovie.presentation.adapters.ItemOffsetDecoration
import com.ruslangrigoriev.topmovie.presentation.adapters.MediaPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment(R.layout.fragment_more) {
    private val binding by viewBinding(FragmentMoreBinding::bind)
    private val viewModel: MoreViewModel by viewModels()
    private val mediaType: String by stringArgs(MEDIA_TYPE)
    private val moreType: String by stringArgs(MORE_TYPE)
    private lateinit var pagingAdapter: MediaPagingAdapter

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbarMore.toolbar)
        binding.toolbarMore.toolbarTitle.text = moreType
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel.moreType = MoreType.valueOf(moreType)
        viewModel.mediaType = MediaType.valueOf(mediaType)
        initRecyclerView()
        subscribeUI()
    }

    private fun initRecyclerView() {
        pagingAdapter = MediaPagingAdapter { id -> onListItemClick(id) }
        val gridLM = GridLayoutManager(
            activity,
            2,
            GridLayoutManager.VERTICAL,
            false
        )
        binding.recyclerViewMore.apply {
            layoutManager = gridLM
            adapter = pagingAdapter
            setHasFixedSize(true)
            addItemDecoration(
                ItemOffsetDecoration(
                    requireContext(),
                    R.dimen.item_offset
                )
            )
        }
    }

    private fun subscribeUI() {
        lifecycleScope.launchWhenStarted {
            viewModel.moreMediaList().collect { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
        pagingAdapter.addLoadStateListener { loadState ->
            val isLoading = loadState.refresh is LoadState.Loading
                    || loadState.append is LoadState.Loading
            binding.progressBarMore.root.isVisible = isLoading
            val errorState = when {
                loadState.append is LoadState.Error -> loadState.append as LoadState.Error
                loadState.prepend is LoadState.Error -> loadState.prepend as LoadState.Error
                loadState.refresh is LoadState.Error -> loadState.refresh as LoadState.Error
                else -> null
            }
            errorState?.error?.message?.showToast(requireContext())
        }
    }

    private fun onListItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putInt(MEDIA_ID, id)
        bundle.putString(MEDIA_TYPE, mediaType)
        findNavController().navigate(R.id.action_moreFragment_to_details, bundle)
    }
}