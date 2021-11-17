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
import com.ruslangrigoriev.topmovie.IMAGE_URL
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.data.model.credits.Cast

class CastAdapter(private var castList: List<Cast>) :
    RecyclerView.Adapter<CastAdapter.ViewHolder>() {


    class ViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        val image: ImageView = itemView.findViewById(R.id.imageView_cast)
        val name: TextView = itemView.findViewById(R.id.textView_cast_name)
        val character: TextView = itemView.findViewById(R.id.textView_cast_character)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cast_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.name.text = castList[position].originalName
        holder.character.text = castList[position].character
        val requestOptions = RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL)
        Glide.with(holder.itemView.context)
            .load(IMAGE_URL + (castList[position].profilePath))
            .apply(requestOptions)
            .thumbnail(0.1f)
            .apply(RequestOptions().override(300, 450))
            .placeholder(R.drawable.placeholder)
            .into(holder.image)
        //downloadImageSmall(castList[position].profilePath,holder.image)
    }

    override fun getItemCount(): Int {
        return castList.size
    }

    fun updateList(castList: List<Cast>) {
        this.castList = castList
        notifyDataSetChanged()
    }
}