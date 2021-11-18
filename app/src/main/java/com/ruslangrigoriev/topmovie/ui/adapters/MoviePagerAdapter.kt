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
import com.ruslangrigoriev.topmovie.data.model.Movie
import com.ruslangrigoriev.topmovie.downloadImageLarge

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
        val image: ImageView = itemView.findViewById(R.id.imageView_poster)
        val title: TextView = itemView.findViewById(R.id.textView_title)
        val date: TextView = itemView.findViewById(R.id.text_view_date)
        val score: TextView = itemView.findViewById(R.id.textView_progressbar)
        val progressBar: ProgressBar = itemView.findViewById(R.id.main_circularProgressbar)
        var id: Int = 0

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            //val position = absoluteAdapterPosition
            onItemClicked(id)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position) // method from PagerAdapter

        holder.id = currentItem?.id!!
        holder.title.text = currentItem.originalTitle ?: currentItem.name
        holder.date.text = currentItem.firstAirDate ?: currentItem.releaseDate
        val score = currentItem.voteAverage
            .toString().replace(".", "")
        holder.score.text = score
        holder.progressBar.progress = score.toInt()
        downloadImageLarge(currentItem.posterPath, holder.image)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MoviePagerAdapter.ViewHolder(view, onItemClicked)
    }
}




