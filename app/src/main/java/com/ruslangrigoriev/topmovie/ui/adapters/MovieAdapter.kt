package com.ruslangrigoriev.topmovie.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.data.model.movies.Movie
import com.ruslangrigoriev.topmovie.downloadImageLarge
import com.ruslangrigoriev.topmovie.loadImageLarge


//UnUsed
class MovieAdapter(
    private val onItemClicked: (position: Int) -> Unit,
    private var moviesList: List<Movie>,
) : RecyclerView.Adapter<MovieAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View,
        private val onItemClicked: (position: Int) -> Unit
    ) : RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val image: ImageView = itemView.findViewById(R.id.imageView_poster)
        private val title: TextView = itemView.findViewById(R.id.textView_title)
        private val date: TextView = itemView.findViewById(R.id.text_view_date)
        private val score: TextView = itemView.findViewById(R.id.textView_progressbar)
        private val progressBar: ProgressBar = itemView.findViewById(R.id.main_circularProgressbar)

        fun bind(movie:Movie){
            itemView.setOnClickListener(this)
            title.text = movie.originalTitle ?: movie.name
            date.text = movie.firstAirDate ?: movie.releaseDate
            score.text = movie.voteAverage.toString().replace(".", "")
            progressBar.progress = movie.voteAverage.toInt()
            image.run{movie.posterPath.loadImageLarge(this)}
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
        holder.bind(moviesList[position])
    }

    override fun getItemCount(): Int {
        return moviesList.size
    }

    fun updateList(moviesList: List<Movie>) {
        this.moviesList = moviesList
        notifyDataSetChanged()
    }

}