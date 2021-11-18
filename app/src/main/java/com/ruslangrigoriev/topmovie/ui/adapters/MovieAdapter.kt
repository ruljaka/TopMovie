package com.ruslangrigoriev.topmovie.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.data.model.Movie
import com.ruslangrigoriev.topmovie.downloadImageLarge

class MovieAdapter(
    private val onItemClicked: (position: Int) -> Unit,
    private var moviesList: List<Movie>,
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        val image: ImageView = itemView.findViewById(R.id.imageView_poster)
        val title: TextView = itemView.findViewById(R.id.textView_title)
        val date: TextView = itemView.findViewById(R.id.text_view_date)
        val score: TextView = itemView.findViewById(R.id.textView_progressbar)
        val progressBar: ProgressBar = itemView.findViewById(R.id.main_circularProgressbar)

        init {
            itemView.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            val position = absoluteAdapterPosition
            onItemClicked(position)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.title.text = moviesList[position].originalTitle ?: moviesList[position].name
        holder.date.text = moviesList[position].firstAirDate ?: moviesList[position].releaseDate
        val score = moviesList[position].voteAverage
            .toString().replace(".", "")
        holder.score.text = score
        holder.progressBar.progress = score.toInt()
        downloadImageLarge(moviesList[position].posterPath, holder.image)
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateList(moviesList: List<Movie>) {
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

}