package com.ruslangrigoriev.topmovie.di

import android.content.Context
import com.ruslangrigoriev.topmovie.data.local.FavoriteDAO
import com.ruslangrigoriev.topmovie.data.remote.ApiService
import com.ruslangrigoriev.topmovie.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    @Singleton
    @Provides
    fun provideRepository(
        @ApplicationContext appContext: Context,
        apiService: ApiService,
        favoriteDAO: FavoriteDAO
    ): Repository {
        return RepositoryImpl(appContext, apiService, favoriteDAO)
    }

    @Singleton
    @Provides
    fun provideAuthRepository(
        @ApplicationContext appContext: Context,
        apiService: ApiService
    ): AuthRepository {
        return AuthRepositoryImpl(appContext, apiService)
    }

    @Singleton
    @Provides
    fun provideMovieRepository(
        apiService: ApiService,
    ): MovieRepository {
        return MovieRepoImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideTvShowRepository(
        apiService: ApiService,
    ): TvShowRepository {
        return TvShowRepoImpl(apiService)
    }

    @Singleton
    @Provides
    fun provideUserRepository(
        @ApplicationContext appContext: Context,
        apiService: ApiService,
        favoriteDAO: FavoriteDAO
    ): UserRepository {
        return UserRepoImpl(appContext, apiService, favoriteDAO)
    }
}