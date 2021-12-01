package com.ruslangrigoriev.topmovie.ui.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.data.model.credits.Cast
import com.ruslangrigoriev.topmovie.utils.IMAGE_URL

class CastAdapter(
    private var castList: List<Cast>,
    private val onItemClicked: (personID: Int) -> Unit,
) : RecyclerView.Adapter<CastAdapter.ViewHolder>() {

    class ViewHolder(
        itemView: View,
        private val onItemClicked: (personID: Int) -> Unit,
    ) :
        RecyclerView.ViewHolder(itemView), View.OnClickListener {
        private val image: ImageView = itemView.findViewById(R.id.imageView_cast)
        private val name: TextView = itemView.findViewById(R.id.textView_cast_name)
        private val character: TextView = itemView.findViewById(R.id.textView_cast_character)
        private var personID: Int = 0

        fun bind(cast: Cast) {
            itemView.setOnClickListener(this)
            personID = cast.id
            name.text = cast.originalName
            character.text = cast.character
            val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
            Glide.with(itemView.context)
                .load(IMAGE_URL + (cast.profilePath))
                .apply(requestOptions)
                .thumbnail(0.1f)
                .apply(RequestOptions().override(300, 450))
                .placeholder(R.drawable.placeholder)
                .into(image)
        }

        override fun onClick(v: View?) {
            onItemClicked(personID)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cast_item, parent, false)
        return ViewHolder(view, onItemClicked)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(castList[position])
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    fun updateList(castList: List<Cast>) {
        this.castList = castList
        notifyDataSetChanged()
    }
}