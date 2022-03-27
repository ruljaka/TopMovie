package com.ruslangrigoriev.topmovie.presentation.video

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.ActivityVideoBinding
import com.ruslangrigoriev.topmovie.domain.utils.MEDIA_ID
import com.ruslangrigoriev.topmovie.domain.utils.MEDIA_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.ResultState.*
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class VideoActivity : AppCompatActivity(R.layout.activity_video) {
    private val binding by viewBinding(ActivityVideoBinding::bind)
    private val viewModel: VideoViewModel by viewModels()
    private var mediaID = 0
    private lateinit var mediaType: String
    private var videoLink: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        subscribeUI()
        loadData()
    }

    private fun loadData() {
        mediaID = intent.getIntExtra(MEDIA_ID, 0)
        mediaType = intent.getStringExtra(MEDIA_TYPE) ?: ""
        viewModel.fetchVideoData(mediaID, mediaType)
    }

    private fun subscribeUI() {
        viewModel.viewState.observe(this, {
            when (it) {
                Loading -> {
                    binding.progressBarVideo.root.isVisible = true
                }
                is Failure -> {
                    showToast(it.errorMessage)
                    binding.progressBarVideo.root.isVisible = false
                }
                is Success -> {
                    binding.progressBarVideo.root.isVisible = false
                    bindUI(it)
                }
            }
        })
    }

    private fun bindUI(it: Success) {
        it.listVideo?.let { list ->
            if (list.isNotEmpty()) {
                videoLink = list[0].key
                videoLink?.let {
                    binding.youTubePlayerView.play(it)
                }
            } else {
                showToast("No videos")
            }
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            this, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }
}