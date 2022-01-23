package com.ruslangrigoriev.topmovie.presentation.video

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.domain.utils.MOVIE_ID
import com.ruslangrigoriev.topmovie.domain.utils.TV_ID
import com.ruslangrigoriev.topmovie.domain.utils.appComponent
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import kr.co.prnd.YouTubePlayerView
import javax.inject.Inject


class VideoActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: VideoViewModel by viewModels { factory }

    private var movieID = 0
    private var tvID = 0
    private var videoLink: String? = null
    private lateinit var youTubePlayerView: YouTubePlayerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video)
        appComponent
            .inject(this)

        youTubePlayerView = findViewById(R.id.you_tube_player_view)
        subscribeUI()
        loadData()
    }

    private fun loadData() {
        movieID = intent.getIntExtra(MOVIE_ID, 0)
        tvID = intent.getIntExtra(TV_ID, 0)
        if (movieID != 0) {
            if (viewModel.movieVideoLD.value == null) {
                viewModel.fetchMovieVideoData(movieID)
            }
        }
        if (tvID != 0) {
            if (viewModel.tvVideoLD.value == null) {
                viewModel.fetchTvVideoData(tvID)
            }
        }
    }

    private fun subscribeUI() {
        viewModel.movieVideoLD.observe(this, { list ->
            videoLink = list[0].key
            videoLink?.let {
                youTubePlayerView.play(it)
            }
        })

        viewModel.tvVideoLD.observe(this, { list ->
            videoLink = list[0].key
            videoLink?.let {
                youTubePlayerView.play(it)
            }
        })
        viewModel.errorLD.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }

}