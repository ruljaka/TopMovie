package com.ruslangrigoriev.topmovie.domain.utils

import android.content.Context
import android.os.Build
import android.util.Log
import android.widget.ImageView
import androidx.annotation.RequiresApi
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.ruslangrigoriev.topmovie.App
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.di.AppComponent
import com.ruslangrigoriev.topmovie.domain.model.details.Genre
import com.ruslangrigoriev.topmovie.domain.model.movies.Movie
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.TimeUnit

val Context.appComponent: AppComponent
    get() = when (this) {
        is App -> appComponent
        else -> applicationContext.appComponent
    }


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


fun Context.saveToFavorites(moveID: Int) {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val oldList = getFavorites()
    if (!oldList.contains(moveID)) {
        oldList.add(moveID)
        with(sharedPref.edit()) {
            putString(FAVORITES, oldList.joinToString())
            apply()
        }
        Log.d("TAG", "saveToFavorite :: ${oldList.joinToString()}")
    }
}

fun Context.removeFromFavorites(moveID: Int) {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val oldList = getFavorites()
    oldList.remove(moveID)
    with(sharedPref.edit()) {
        putString(FAVORITES, oldList.joinToString())
        apply()
    }
    Log.d("TAG", "removeFromFavorites :: ${oldList.joinToString()}")
}

fun Context.getFavorites(): MutableList<Int> {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val favoriteMovies = sharedPref.getString(FAVORITES, "") ?: ""
    val favoriteList = favoriteMovies.splitToSequence(", ")
        .filter { it.isNotEmpty() }
        .map { it.toInt() }
        .toMutableList()
    Log.d("TAG", "getFavorites :: $favoriteList")
    return favoriteList
}


fun checkFavorites(savedFavorites: List<Int>, movieID: Int): Boolean {
    return savedFavorites.contains(movieID)
}

 fun Context.onBoardingFinished(){
     val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
     val editor = sharedPref.edit()
     editor.putBoolean(OB_FINISHED,true)
     editor.apply()
}

fun Context.onBoardingIsFinished(): Boolean{
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(OB_FINISHED,false)
}







