package com.ruslangrigoriev.topmovie.domain.utils.extensions

import android.content.Context
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.domain.utils.IMAGE_URL_W1280
import com.ruslangrigoriev.topmovie.domain.utils.IMAGE_URL_W500
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*

fun String.formatDate(): String {
    val firstFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
    if (this.isNotEmpty()) {
        val localDate = LocalDate.parse(this, firstFormatter)
        val secondFormatter = DateTimeFormatter.ofPattern("yyyy", Locale.ENGLISH)
        return localDate.format(secondFormatter)
    }
    return "not found"
}

fun String.loadPosterLarge(imageView: ImageView) {
    Glide.with(imageView.context)
        .load(IMAGE_URL_W500 + this)
        .apply(RequestOptions().override(400, 600))
        .centerCrop()
        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
        .placeholder(R.drawable.placeholder)
        .thumbnail(0.25f)
        .into(imageView)
}

fun String.loadPosterSmall(imageView: ImageView) {
    Glide.with(imageView.context)
        .load(IMAGE_URL_W500 + this)
        .apply(RequestOptions().override(300, 450))
        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
        .placeholder(R.drawable.placeholder)
        .thumbnail(0.25f)
        .into(imageView)
}

fun String.loadBackDropImage(imageView: ImageView) {
    Glide.with(imageView.context)
        .load(IMAGE_URL_W1280 + this)
        .apply(RequestOptions.bitmapTransform(RoundedCorners(30)))
        .apply(RequestOptions().diskCacheStrategy(DiskCacheStrategy.ALL))
        .placeholder(R.drawable.placeholder)
        .thumbnail(0.25f)
        .into(imageView)
}

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}