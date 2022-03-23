package com.ruslangrigoriev.topmovie.domain.utils

import android.content.Context
import android.os.Build
import android.widget.ImageView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.bitmap.RoundedCorners
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.ruslangrigoriev.topmovie.R
import com.ruslangrigoriev.topmovie.data.api.dto.Genre
import com.ruslangrigoriev.topmovie.data.api.dto.favorite.FavoriteResponse
import com.ruslangrigoriev.topmovie.data.api.dto.auth.RequestToken
import com.ruslangrigoriev.topmovie.data.api.dto.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.Media
import com.ruslangrigoriev.topmovie.data.api.dto.movies.Movie
import com.ruslangrigoriev.topmovie.data.api.dto.tv.TvShow
import com.ruslangrigoriev.topmovie.domain.model.mappers.MovieMapper
import com.ruslangrigoriev.topmovie.domain.model.mappers.TvMapper
import retrofit2.Response
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.*
import kotlin.properties.ReadOnlyProperty

fun stringArgs(key: String): ReadOnlyProperty<Fragment, String> {
    return ReadOnlyProperty { thisRef, _ ->
        val args = thisRef.requireArguments()
        require(args.containsKey(key)) { "Arguments don't contain key '$key'" }
        requireNotNull(args.getString(key))
    }
}

fun intArgs(key: String): ReadOnlyProperty<Fragment, Int> {
    return ReadOnlyProperty { thisRef, _ ->
        val args = thisRef.requireArguments()
        require(args.containsKey(key)) { "Arguments don't contain key '$key'" }
        requireNotNull(args.getInt(key))
    }
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

fun List<Media>.getTopPersonCasts(): List<Media> {
    val sortedList = this.toMutableList().sortedByDescending { it.popularity }
    return if (sortedList.size > 20) {
        sortedList.subList(0, 20)
    } else {
        sortedList
    }
}

fun List<Cast>.getTopCast(): List<Cast> {
    val sortedList = this.toMutableList().sortedByDescending { it.popularity }
    return if (sortedList.size > 15) {
        sortedList.subList(0, 15)
    } else {
        sortedList
    }
}

fun Context.onBoardingFinished() {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putBoolean(OB_FINISHED, true)
    editor.apply()
}

fun Context.onBoardingIsFinished(): Boolean {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return sharedPref.getBoolean(OB_FINISHED, false)
}

fun <T : Any> getResultOrError(response: Response<T>): T? {
    if (response.isSuccessful) {
        return response.body()
    } else {
        try {
            val responseError = Gson().fromJson(
                response.errorBody()?.string(),
                FavoriteResponse::class.java
            )
            throw Throwable(responseError.statusMessage)
        } catch (e: Exception) {
            throw Throwable("Unknown error")
        }
    }
}

fun mapMovieToMedia(moviesList: List<Movie>?): List<Media> {
    return moviesList?.map {
        MovieMapper.map(it)
    } ?: emptyList()
}

fun mapTvShowToMedia(tvList: List<TvShow>?): List<Media> {
    return tvList?.map {
        TvMapper.map(it)
    } ?: emptyList()
}

fun Context.saveSessionID(sessionID: String) {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putString(SESSION, sessionID)
    editor.apply()
}

fun Context.getSessionID(): String? {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return sharedPref.getString(SESSION, "")
}

fun Context.saveUserID(userID: Int) {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    editor.putInt(USER_ID, userID)
    editor.apply()
}

fun Context.getUserID(): Int {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    return sharedPref.getInt(USER_ID, 0)

}

fun Context.saveAuthToken(requestToken: RequestToken) {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val editor = sharedPref.edit()
    val jsonString = Gson().toJson(requestToken)
    editor.putString(REQUEST_TOKEN, jsonString)
    editor.apply()
}

fun Context.getAuthToken(): RequestToken? {
    val sharedPref = this.getSharedPreferences(SHARED_PREF, Context.MODE_PRIVATE)
    val jsonString = sharedPref.getString(REQUEST_TOKEN, "")
    jsonString?.let {
        return Gson().fromJson(jsonString, RequestToken::class.java)
    }
    return null
}

fun String.showToast(context: Context) {
    Toast.makeText(context, this, Toast.LENGTH_SHORT).show()
}












