package com.ruslangrigoriev.topmovie.utils

import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.data.model.details.Genre
import com.ruslangrigoriev.topmovie.data.model.movies.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

fun fromMinutesToHHmm(minutes: Int): String {
    val hours = TimeUnit.MINUTES.toHours(minutes.toLong())
    val remainMinutes = minutes - TimeUnit.HOURS.toMinutes(hours)
    return String.format("%01dh %02dm", hours, remainMinutes)
}

fun getNamesFromGenre(genres: List<Genre>): String {
    val listGenreNames: List<String> = genres.map { it.name }
    return listGenreNames.joinToString(", ")
}

@RequiresApi(Build.VERSION_CODES.O)
fun String.formatDate(): String {
    val firstFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    if (this.isNotEmpty()) {
        val localDate = LocalDate.parse(this, firstFormatter)
        val secondFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
        return localDate.format(secondFormatter)
    }
    return "not found"
}

fun String.loadImageLarge(imageView: ImageView) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(imageView.context)
        .load(IMAGE_URL + this)
        .apply(requestOptions)
        .apply(RequestOptions().override(360, 540))
        .apply(RequestOptions.bitmapTransform(RoundedCorners(24)))
        .placeholder(R.drawable.placeholder)
        .thumbnail(0.25f)
        .into(imageView)
}

fun String.loadImageSmall(imageView: ImageView) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(imageView.context)
        .load(IMAGE_URL + this)
        .apply(requestOptions)
        .thumbnail(0.25f)
        .apply(RequestOptions().override(300, 450))
        .apply(RequestOptions.bitmapTransform(RoundedCorners(14)))
        .placeholder(R.drawable.placeholder)
        .into(imageView)
}

fun List<Movie>.getTopPersonCasts(): List<Movie> {
    val sortedList = this.toMutableList().sortedByDescending { it.voteCount }
    return if (sortedList.size > 15) {
        sortedList.subList(0, 15)
    } else {
        sortedList
    }

}




