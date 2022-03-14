package com.ruslangrigoriev.topmovie.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.utils.formatDate
import com.ruslangrigoriev.topmovie.domain.utils.loadPosterLarge

class MediaPagingAdapter(
    private val onItemClicked: (id: Int) -> Unit,
) : PagingDataAdapter<Media, MediaPagingAdapter.MediaViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<Media>() {
            override fun areItemsTheSame(oldItem: Media, newItem: Media): Boolean {
                return oldItem.id == newItem.id

            }

            override fun areContentsTheSame(oldItem: Media, newItem: Media): Boolean {
                return oldItem == newItem
            }
        }
    }

    open class MediaViewHolder(
        itemView: View,
        private val onItemClicked: (id: Int) -> Unit,
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {

        private val image: ImageView = itemView.findViewById(R.id.imageView_popular_poster)
        private val title: TextView = itemView.findViewById(R.id.textView_popular_title)
        private val date: TextView = itemView.findViewById(R.id.textView_popular_date)
        private val score: TextView = itemView.findViewById(R.id.textView_popular_score)
        var id: Int = 0

        open fun bindView(media: Media) {
            id = media.id
            title.text = media.originalTitle
            date.text = media.releaseDate?.formatDate()
            score.text = media.voteAverage.toString()
            media.posterPath?.loadPosterLarge(image)
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClicked(id)
        }
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val currentItem = getItem(position) // method from PagerAdapter
        if (currentItem != null) {
            holder.bindView(currentItem)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_movie_popular, parent, false)
        return MediaViewHolder(view, onItemClicked)

    }
}




