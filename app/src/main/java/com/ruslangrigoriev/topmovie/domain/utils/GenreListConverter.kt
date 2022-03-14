package com.ruslangrigoriev.topmovie.domain.utils

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.ruslangrigoriev.topmovie.data.api.dto.Genre

class GenreListConverter {
    private val gson = Gson()

    @TypeConverter
    fun stringToList(data: String?): List<Genre>? {
        return data?.let{
            val listType = object : TypeToken<List<Genre>?>() {}.type
            return gson.fromJson(data, listType)
        }
    }

    @TypeConverter
    fun listToString(dataItems: List<Genre>?): String? {
        return gson.toJson(dataItems)
    }
}