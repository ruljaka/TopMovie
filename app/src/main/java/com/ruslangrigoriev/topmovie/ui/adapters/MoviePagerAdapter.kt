package com.ruslangrigoriev.topmovie.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.data.model.movies.Movie
import com.ruslangrigoriev.topmovie.utils.loadImageLarge

class MoviePagerAdapter(
    private val onItemClicked: (id: Int) -> Unit,
) : PagingDataAdapter<Movie, MoviePagerAdapter.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Movie>() {
            override fun areItemsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: Movie, newItem: Movie): Boolean {
                return oldItem == newItem
            }
        }
    }

    class ViewHolder(
        itemView: View,
        private val onItemClicked: (id: Int) -> Unit,
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val image: ImageView = itemView.findViewById(R.id.imageView_poster)
        private val title: TextView = itemView.findViewById(R.id.textView_title)
        private val date: TextView = itemView.findViewById(R.id.text_view_date)
        private val score: TextView = itemView.findViewById(R.id.textView_progressbar)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.main_circularProgressbar)
        var id: Int = 0

        fun bind(movie: Movie) {
            id = movie.id
            itemView.setOnClickListener(this)
            title.text = movie.originalTitle ?: movie.name
            date.text = movie.firstAirDate ?: movie.releaseDate
            score.text = movie.voteAverage.toString().replace(".", "")
            progressBar.progress = movie.voteAverage.toString().replace(".", "").toInt()
            movie.posterPath?.loadImageLarge(image)
        }

        override fun onClick(v: View?) {
            onItemClicked(id)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position) // method from PagerAdapter
        if (currentItem != null) {
            holder.bind(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MoviePagerAdapter.ViewHolder(view, onItemClicked)
    }
}




