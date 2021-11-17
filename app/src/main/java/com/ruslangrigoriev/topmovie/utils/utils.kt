package com.ruslangrigoriev.topmovie

import android.os.Build
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ruslangrigoriev.topmovie.data.model.Details.Genre
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
fun formatDate(incomingDate: String): String {
    val firstFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    val localDate = LocalDate.parse(incomingDate, firstFormatter)
    val secondFormatter = DateTimeFormatter.ofPattern("dd MMM yyyy", Locale.ENGLISH)
    return localDate.format(secondFormatter)
}

fun downloadImageSmall(path: String?, imageView: ImageView) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(imageView.context)
        .load(IMAGE_URL + path)
        .apply(requestOptions)
        .thumbnail(0.25f)
        .apply(RequestOptions().override(300, 450))
        .apply(RequestOptions.bitmapTransform(RoundedCorners(15)))
        .placeholder(R.drawable.placeholder)
        .into(imageView)
}

fun downloadImageLarge(path: String?, imageView: ImageView) {
    val requestOptions = RequestOptions()
        .diskCacheStrategy(DiskCacheStrategy.ALL)
    Glide.with(imageView.context)
        .load(IMAGE_URL + path)
        .apply(requestOptions)
        .thumbnail(0.25f)
        .apply(RequestOptions().override(360, 540))
        .apply(RequestOptions.bitmapTransform(RoundedCorners(25)))
        .placeholder(R.drawable.placeholder)
        .into(imageView)
}


