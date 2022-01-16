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
import com.ruslangrigoriev.topmovie.domain.model.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.utils.loadPosterLarge

class TvPagingAdapter(
    private val onItemClicked: (id: Int) -> Unit,
) : PagingDataAdapter<TvShow, TvPagingAdapter.ViewHolder>(diffCallback) {

    companion object {
        val diffCallback = object : DiffUtil.ItemCallback<TvShow>() {
            override fun areItemsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
                return oldItem.id == newItem.id
            }

            override fun areContentsTheSame(oldItem: TvShow, newItem: TvShow): Boolean {
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
        private val favorite: ImageView = itemView.findViewById(R.id.imageView_favorite)
        var id: Int = 0

        fun bind(tvShow: TvShow) {
            id = tvShow.id
            itemView.setOnClickListener(this)
            title.text = tvShow.originalName
            date.text = tvShow.firstAirDate
            score.text = tvShow.voteAverage.toString().replace(".", "")
            progressBar.progress = tvShow.voteAverage.toString().replace(".", "").toInt()
            tvShow.posterPath?.loadPosterLarge(image)
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
            .inflate(R.layout.item_movie, parent, false)
        return ViewHolder(view, onItemClicked)
    }
}




