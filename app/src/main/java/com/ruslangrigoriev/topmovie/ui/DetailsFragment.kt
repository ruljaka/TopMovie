package com.ruslangrigoriev.topmovie.ui

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.Glide
import com.ruslangrigoriev.topmovie.*
import com.ruslangrigoriev.topmovie.databinding.DetailsFragmentBinding
import com.ruslangrigoriev.topmovie.ui.adapters.CastAdapter
import com.ruslangrigoriev.topmovie.viewmodel.DetailsViewModel
import com.ruslangrigoriev.topmovie.viewmodel.MainViewModel
import com.ruslangrigoriev.topmovie.viewmodel.MyViewModelFactory

class DetailsFragment : Fragment() {
    private var _binding: DetailsFragmentBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: DetailsViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = DetailsFragmentBinding.inflate(inflater, container, false)

        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        //get Data
        val id = arguments?.getInt(ID)
        if (id != null) {
            viewModel =
                ViewModelProvider(this, MyViewModelFactory(id = id))[DetailsViewModel::class.java]
            viewModel.getDetails(id)
            viewModel.getCast(id)
        }

        //set castAdapter
        val castAdapter = CastAdapter(emptyList())
        binding.recyclerViewDetails.layoutManager =
            LinearLayoutManager(view.context, LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerViewDetails.adapter = castAdapter

        //update UI
        viewModel.detailsLD.observe(viewLifecycleOwner, {
            binding.textViewDetailsTitle.text = it.title
            binding.textViewDetailsData.text = ("Released: " + formatDate(it.releaseDate))
            binding.textViewDetailsRuntime.text = fromMinutesToHHmm(it.runtime)
            binding.textViewDetailsGenre.text = getNamesFromGenre(it.genres)
            binding.textViewDetailsScore.text = (it.voteAverage.toString() + " User Score")
            binding.textViewDetailsOverview.text = it.overview
            val score = it.voteAverage.toString()
                .replace(".", "")
            binding.textViewDetailsProgressbar.text = score
            binding.detailsCircularProgressbar.progress = score.toInt()
            downloadImageSmall(it.posterPath, binding.imageViewDetailsPoster)
            Glide.with(view.context)
                .load(IMAGE_URL + (it.backdropPath))
                .into(binding.imageViewDetailsBackPoster)
        })
        viewModel.castLD.observe(viewLifecycleOwner, {
            castAdapter.updateList(it)
        })
        viewModel.errorLD.observe(viewLifecycleOwner, {
            Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }


}