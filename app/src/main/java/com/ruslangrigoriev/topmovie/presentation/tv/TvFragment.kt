package com.ruslangrigoriev.topmovie.presentation.tv

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.MainActivity
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.FragmentTvBinding
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.adapters.BaseRecyclerAdapter
import com.ruslangrigoriev.topmovie.presentation.adapters.BindingInterface
import com.ruslangrigoriev.topmovie.presentation.tv.ResultTvState.*
import javax.inject.Inject

class TvFragment : Fragment(R.layout.fragment_tv) {
    private val binding by viewBinding(FragmentTvBinding::bind)

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: TvViewModel by viewModels { factory }

    private lateinit var nowRecyclerAdapter: BaseRecyclerAdapter<Media>
    private lateinit var popularRecyclerAdapter: BaseRecyclerAdapter<Media>

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        (requireActivity() as MainActivity).setupToolbar(binding.toolbarTv.toolbar)
        binding.toolbarTv.toolbarTitle.text = "TV"
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
            viewModel.fetchData()
        }
    }

    private fun subscribeUi() {
        lifecycleScope.launchWhenStarted {
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
    }

    private fun bindUI(it: Success) {
        nowRecyclerAdapter.updateList(it.nowList)
        popularRecyclerAdapter.updateList(it.popularList)
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
        binding.recyclerViewTvNow.apply {
            layoutManager =
                LinearLayoutManager(
                    activity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            adapter = nowRecyclerAdapter
            setHasFixedSize(true)
        }
    }

    private fun setPopularRecView() {
        val bindingInterface = object : BindingInterface<Media> {
            override fun bindData(item: Media, view: View) {
                val name: TextView = view.findViewById(R.id.textView_tv_popular_name)
                name.text = item.title
                val date: TextView = view.findViewById(R.id.textView_tv_popular_date)
                date.text = item.releaseDate?.formatDate()
                val vote: TextView = view.findViewById(R.id.textView_tv_popular_score)
                vote.text = item.voteAverage.toString()
                val poster: ImageView = view.findViewById(R.id.imageView_tv_popular_poster)
                item.backdropPath?.loadBackDropImage(poster)
                //item.posterPath?.loadTvPosterLarge(poster)
                view.setOnClickListener {
                    onListItemClick(item.id)
                }
            }
        }
        popularRecyclerAdapter = BaseRecyclerAdapter(
            emptyList(),
            R.layout.item_tv_popular,
            bindingInterface,
        )
        binding.recyclerViewTvPopular.apply {
            layoutManager =
                LinearLayoutManager(
                    activity,
                    LinearLayoutManager.VERTICAL,
                    false
                )
            adapter = popularRecyclerAdapter
            setHasFixedSize(true)
        }
    }

    private fun setupSearch() {
        binding.toolbarTv.searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
            androidx.appcompat.widget.SearchView.OnQueryTextListener {
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