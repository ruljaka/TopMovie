package com.ruslangrigoriev.topmovie.presentation.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.databinding.ItemMediaBinding
import com.ruslangrigoriev.topmovie.databinding.ItemMoreBinding
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.domain.utils.FOOTER_VIEW_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.REGULAR_VIEW_TYPE
import com.ruslangrigoriev.topmovie.domain.utils.formatDate
import com.ruslangrigoriev.topmovie.domain.utils.loadPosterLarge


class MainTabsRecyclerAdapter(
    private var dataList: List<Media>,
    private val onItemClicked: (id: Int) -> Unit
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    inner class ViewHolder(
        private val binding: ItemMediaBinding,
        private val onItemClicked: (id: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        init {
            binding.root.setOnClickListener{
                onItemClicked(dataList[absoluteAdapterPosition].id)
            }
        }

        fun bind(position: Int) {
            val item = dataList[position]
            with(binding){
                textViewMediaTitle.text = item.title
                textViewMediaDate.text = item.releaseDate?.formatDate()
                textViewMediaScore.text = item.voteAverage.toString()
                item.posterPath?.loadPosterLarge(imageViewMediaPoster)
            }
        }
    }

    class FooterHolder(
        private val binding: ItemMoreBinding,
        private val onItemClicked: (id: Int) -> Unit
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind() {

        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            REGULAR_VIEW_TYPE -> {
                val binding = ItemMediaBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false)
                ViewHolder(binding, onItemClicked)
            }
            else -> {
                val binding = ItemMoreBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent, false)
                FooterHolder(binding, onItemClicked)
            }
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is ViewHolder) holder.bind(position)
        else if (holder is FooterHolder) holder.bind()
    }

    override fun getItemCount(): Int = dataList.size

    fun updateList(list: List<Media>) {
        this.dataList = list
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == dataList.size - 1) FOOTER_VIEW_TYPE else REGULAR_VIEW_TYPE
    }
}

