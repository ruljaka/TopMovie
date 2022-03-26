package com.ruslangrigoriev.topmovie.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.topmovie.databinding.ItemMediaBinding
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.utils.extensions.loadPosterLarge

class MediaPagingAdapter(
    private val onItemClicked: (id: Int) -> Unit,
) : PagingDataAdapter<Media, MediaPagingAdapter.MediaViewHolder>(diffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MediaViewHolder {
        val binding = ItemMediaBinding.inflate(
            LayoutInflater.from(parent.context),
            parent, false
        )
        return MediaViewHolder(binding, onItemClicked)
    }

    override fun onBindViewHolder(holder: MediaViewHolder, position: Int) {
        val currentItem = getItem(position) // method from PagerAdapter
        if (currentItem != null) {
            holder.bindView(currentItem)
        }
    }

    class MediaViewHolder(
        private val binding: ItemMediaBinding,
        private val onItemClicked: (id: Int) -> Unit,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bindView(item: Media) {
            with(binding) {
                textViewMediaTitle.text = item.title
                textViewMediaDate.text = item.releaseDate
                textViewMediaScore.text = item.voteAverage.toString()
                item.posterPath?.loadPosterLarge(imageViewMediaPoster)
                root.setOnClickListener {
                    onItemClicked(item.id)
                }
            }
        }
    }

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
}




