package com.ruslangrigoriev.topmovie.data.api

import com.ruslangrigoriev.topmovie.BASE_URL
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

object ApiFactory {

    private fun retrofitService(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val getApiService: ApiService by lazy {
        retrofitService().create(ApiService::class.java)
    }
}