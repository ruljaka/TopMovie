package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.domain.model.ResponseObject
import com.ruslangrigoriev.topmovie.domain.dto.credits.Cast
import com.ruslangrigoriev.topmovie.domain.model.media.Media
import com.ruslangrigoriev.topmovie.domain.dto.movies.MovieResponse
import com.ruslangrigoriev.topmovie.domain.dto.person.Person
import com.ruslangrigoriev.topmovie.domain.dto.profile.User
import com.ruslangrigoriev.topmovie.domain.dto.tv.TvResponse
import com.ruslangrigoriev.topmovie.domain.dto.video.Video

interface Repository {
//    suspend fun getMoviesTrending(page: Int): List<Media>
//    suspend fun getMoviesNow(): List<Media>
//    suspend fun getMoviesPopular(): List<Media>
//    suspend fun getMovieDetails(id: Int): Media?
//    suspend fun getMovieCredits(id: Int): List<Cast>
//    suspend fun searchMoviesPaged(query: String, page: Int): List<Media>
//
//    suspend fun getTvNow(): List<Media>
//    suspend fun getTvPopular(): List<Media>
//    suspend fun getTvDetails(id: Int): Media?
//    suspend fun getTvCredits(id: Int): List<Cast>
//    suspend fun searchTvPaged(query: String, page: Int): List<Media>

    suspend fun getPerson(person_id: Int): Person?
    suspend fun getPersonCredits(person_id: Int): List<Media>

    suspend fun getMovieVideo(movie_id: Int): List<Video>
    suspend fun getTvVideo(tv_id: Int): List<Video>

//    suspend fun getUserData(): User?
//    suspend fun getRatedMovies(accountID: Int): MovieResponse?
//    suspend fun getRatedTvShows(accountID: Int): TvResponse?
//    suspend fun getFavoriteMovies(accountID: Int): List<Media>?
//    suspend fun getFavoriteTvShows(accountID: Int): List<Media>?
//
//    fun saveUserID(userID: Int)
//    suspend fun markFavorite(mediaType: String, media_id: Int): ResponseObject?
}