package com.ruslangrigoriev.topmovie.presentation.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import com.ruslangrigoriev.topmovie.domain.utils.loadImageSmall

class PersonCastAdapter(
    private var personCastList: List<Movie>,
    private val onItemClicked: (movieID: Int) -> Unit,
) : RecyclerView.Adapter<PersonCastAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View,
        private val onItemClicked: (movieID: Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val image: ImageView = itemView.findViewById(R.id.imageView_person_cast_poster)
        private val name: TextView = itemView.findViewById(R.id.textView_person_cast_movie_name)
        private var movieID: Int = 0

        fun bind(movie: Movie) {
            itemView.setOnClickListener(this)
            movieID = movie.id
            name.text = movie.originalTitle ?: movie.title
            movie.posterPath?.loadImageSmall(image)
        }

        override fun onClick(v: View?) {
            onItemClicked(movieID)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_cast_item, parent, false)
        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(personCastList[position])
    }

    override fun getItemCount(): Int {
        return personCastList.size
    }

    fun updateList(personCastList: List<Movie>) {
        this.personCastList = personCastList
        notifyDataSetChanged()
    }
}