package com.ruslangrigoriev.topmovie.presentation.more

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentMoreBinding
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.presentation.MainActivity
import com.ruslangrigoriev.topmovie.presentation.adapters.MediaPagingAdapter
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MoreFragment : Fragment(R.layout.fragment_more) {
    private val binding by viewBinding(FragmentMoreBinding::bind)
    private val viewModel: MoreViewModel by viewModels()
    private val mediaType: String by stringArgs(MEDIA_TYPE)
    private val moreType: String by stringArgs(MORE_TYPE)
    private lateinit var pagingAdapter: MediaPagingAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbarMore.toolbar)
        binding.toolbarMore.toolbarTitle.text = "MORE $moreType"

        viewModel.moreType = MoreType.valueOf(moreType)
        viewModel.mediaType = MediaType.valueOf(mediaType)

        subscribeUI()

    }

    private fun subscribeUI() {

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
        }

        lifecycleScope.launchWhenStarted {
            viewModel.moreMediaList().collect { pagingData ->
                pagingAdapter.submitData(pagingData)
            }
        }
    }

    private fun onListItemClick(id: Int) {
        val bundle = Bundle()
        bundle.putInt(MEDIA_ID, id)
        bundle.putString(MEDIA_TYPE, MOVIE_TYPE)
        findNavController().navigate(R.id.action_moreFragment_to_details, bundle)
    }


}