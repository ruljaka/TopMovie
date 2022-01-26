package com.ruslangrigoriev.topmovie.domain.model

interface ContentType {
    companion object{
        val TYPE_MOVIE: Int = 101
        var TYPE_TvSHOW: Int = 102
    }

    fun getType(): Int
}