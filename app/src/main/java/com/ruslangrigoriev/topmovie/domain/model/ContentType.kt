package com.ruslangrigoriev.topmovie.domain.model

interface ContentType {
    companion object{
        const val TYPE_MOVIE: Int = 101
        const val TYPE_TvSHOW: Int = 102
    }

    fun getType(): Int
}