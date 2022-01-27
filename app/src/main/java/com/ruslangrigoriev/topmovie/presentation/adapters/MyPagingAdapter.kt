package com.ruslangrigoriev.topmovie.presentation.adapters

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
import com.ruslangrigoriev.topmovie.domain.model.ContentType
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.utils.loadPosterLarge

class MyPagingAdapter(
    private val onItemClicked: (id: Int) -> Unit,
) : PagingDataAdapter<ContentType, MyPagingAdapter.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<ContentType>() {
            override fun areItemsTheSame(oldItem: ContentType, newItem: ContentType): Boolean {
                return if (oldItem.getType() == ContentType.TYPE_MOVIE) {
                    (oldItem as Movie).id == (newItem as Movie).id
                } else {
                    (oldItem as TvShow).id == (newItem as TvShow).id
                }
            }

            override fun areContentsTheSame(oldItem: ContentType, newItem: ContentType): Boolean {
                return if (oldItem.getType() == ContentType.TYPE_MOVIE) {
                    (oldItem as Movie) == (newItem as Movie)
                } else {
                    (oldItem as TvShow) == (newItem as TvShow)
                }
            }
        }
    }

    open class ViewHolder(
        itemView: View,
        private val onItemClicked: (id: Int) -> Unit,
    ) : RecyclerView.ViewHolder(itemView) {

        open fun bindView(content: ContentType) {
        }
    }

    class MovieViewHolder(
        itemView: View,
        private val onItemClicked: (id: Int) -> Unit,
    ) : MyPagingAdapter.ViewHolder(itemView, onItemClicked), View.OnClickListener {
        private val image: ImageView = itemView.findViewById(R.id.imageView_poster)
        private val title: TextView = itemView.findViewById(R.id.textView_title)
        private val date: TextView = itemView.findViewById(R.id.text_view_date)
        private val score: TextView = itemView.findViewById(R.id.textView_progressbar)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.main_circularProgressbar)
        var id: Int = 0

        override fun bindView(content: ContentType) {
            val movie = content as Movie
            id = movie.id
            itemView.setOnClickListener(this)
            title.text = movie.originalTitle ?: movie.title
            date.text = movie.releaseDate
            score.text = movie.voteAverage.toString().replace(".", "")
            progressBar.progress = movie.voteAverage.toString().replace(".", "").toInt()
            movie.posterPath?.loadPosterLarge(image)
        }

        override fun onClick(v: View?) {
            onItemClicked(id)
        }

    }

    class TvViewHolder(
        itemView: View,
        private val onItemClicked: (id: Int) -> Unit,
    ) : MyPagingAdapter.ViewHolder(itemView, onItemClicked), View.OnClickListener {
        private val image: ImageView = itemView.findViewById(R.id.imageView_poster)
        private val title: TextView = itemView.findViewById(R.id.textView_title)
        private val date: TextView = itemView.findViewById(R.id.text_view_date)
        private val score: TextView = itemView.findViewById(R.id.textView_progressbar)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.main_circularProgressbar)
        var id: Int = 0

        override fun bindView(content: ContentType) {
            val movie = content as TvShow
            id = movie.id
            itemView.setOnClickListener(this)
            title.text = movie.originalName
            date.text = movie.firstAirDate
            score.text = movie.voteAverage.toString().replace(".", "")
            progressBar.progress = movie.voteAverage.toString().replace(".", "").toInt()
            movie.posterPath?.loadPosterLarge(image)
        }

        override fun onClick(v: View?) {
            onItemClicked(id)
        }

    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val currentItem = getItem(position) // method from PagerAdapter
        if (currentItem != null) {
            holder.bindView(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie, parent, false)
        return if (viewType == ContentType.TYPE_MOVIE) {
            MovieViewHolder(view, onItemClicked)
        } else {
            TvViewHolder(view, onItemClicked)
        }
    }


    override fun getItemViewType(position: Int): Int {
        //return super.getItemViewType(position)
        return getItem(position)?.getType()
            ?: super.getItemViewType(position)

    }


}




