package com.ruslangrigoriev.topmovie.presentation.video

import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.domain.utils.*
import com.ruslangrigoriev.topmovie.presentation.MyViewModelFactory
import kr.co.prnd.YouTubePlayerView
import javax.inject.Inject


class VideoActivity : AppCompatActivity() {

    @Inject
    lateinit var factory: MyViewModelFactory
    private val viewModel: VideoViewModel by viewModels { factory }

    private var mediaID = 0
    private var sourceType: String? = null
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
        mediaID = intent.getIntExtra(MEDIA_ID, 0)
        sourceType = intent.getStringExtra(SOURCE_TYPE)
        if (sourceType == MOVIE_TYPE) {
            viewModel.fetchMovieVideoData(mediaID)
        }
        if (sourceType == TV_TYPE) {
            viewModel.fetchTvVideoData(mediaID)
        }
    }

    private fun subscribeUI() {
        viewModel.videoLD.observe(this, { list ->
            if (list.isNotEmpty()) {
                videoLink = list[0].key
                videoLink?.let {
                    youTubePlayerView.play(it)
                }
            } else {
                Toast.makeText(this, "No videos", Toast.LENGTH_LONG).show()
            }
        })

        viewModel.errorLD.observe(this, {
            Toast.makeText(this, it, Toast.LENGTH_LONG).show()
        })
    }
}