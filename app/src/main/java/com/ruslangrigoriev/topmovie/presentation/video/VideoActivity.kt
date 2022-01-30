package com.ruslangrigoriev.topmovie.presentation.video

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import by.kirich1409.viewbindingdelegate.viewBinding
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.ActivityVideoBinding
import com.ruslangrigoriev.topmovie.domain.utils.MEDIA_ID
import com.ruslangrigoriev.topmovie.domain.utils.MEDIA_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.appComponent
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import com.ruslangrigoriev.topmovie.presentation.video.ResultVideoState.*
import kr.co.prnd.YouTubePlayerView
import javax.inject.Inject


class VideoActivity : AppCompatActivity(R.layout.activity_video) {
    private val binding by viewBinding(ActivityVideoBinding::bind)

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: VideoViewModel by viewModels { factory }

    private var mediaID = 0
    private lateinit var mediaType: String
    private var videoLink: String? = null
    private lateinit var youTubePlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        appComponent.inject(this)

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
        youTubePlayerView = findViewById(R.id.you_tube_player_view)
        if (it.listVideo.isNotEmpty()) {
            videoLink = it.listVideo[0].key
            videoLink?.let {
                youTubePlayerView.play(it)
            }
        } else {
            showToast("No videos")
        }
    }

    private fun showLoading(loading: Boolean) {
        if (loading) {
            binding.progressBarVideo.visibility = View.VISIBLE
        } else {
            binding.progressBarVideo.visibility = View.GONE
        }
    }

    private fun showToast(message: String?) {
        Toast.makeText(
            this, message ?: "Unknown Error", Toast.LENGTH_SHORT
        ).show()
    }
}