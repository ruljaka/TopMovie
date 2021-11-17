package com.ruslangrigoriev.topmovie.data.repository

import com.ruslangrigoriev.topmovie.data.api.ApiFactory

object RepositoryImpl : Repository {

    private val apiService = ApiFactory.getApiService

//    private val trendingMoviesLD = MutableLiveData<List<Movie>>()
//    private val detailsLD = MutableLiveData<Details>()
//    private val castLD = MutableLiveData<List<Cast>>()
//    private val searchMoviesLD = MutableLiveData<List<Movie>>()
//    val errorLD = MutableLiveData<String>()
//    private var compositeDisposable: CompositeDisposable? = null


    override suspend fun getTrending() = apiService.getTrending()
    override suspend fun getDetails(id: Int) = apiService.getDetails(id)
    override suspend fun getCast(id: Int) = apiService.getCast(id)
    override suspend fun getSearchResult(query: String) = apiService.searchMovie(query = query)

//    override fun getMovies(): MutableLiveData<List<Movie>> {
//        val trendingDisposable = apiService.getTrending()
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                trendingMoviesLD.value = it.movies
//            }, {
//                errorLD.value = it.message
//                Log.d("TAG", "getMovies Response error: " + it.message.toString())
//            })
//        compositeDisposable?.add(trendingDisposable)
//        return trendingMoviesLD
//    }

//    override fun getDetails(id: Int): MutableLiveData<Details> {
//        compositeDisposable = CompositeDisposable()
//        val detailsDisposable = apiService.getDetails(id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                detailsLD.value = it
//            }, {
//                errorLD.value = it.message
//                Log.d("TAG", "getDetails Response error: " + it.message.toString())
//            })
//        compositeDisposable?.add(detailsDisposable)
//        return detailsLD
//    }

//    override fun getCast(id: Int): MutableLiveData<List<Cast>> {
//        compositeDisposable = CompositeDisposable()
//        val castDisposable = apiService.getCredits(id)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                castLD.value = it.cast
//            }, {
//                errorLD.value = it.message
//                Log.d("TAG", "getCast Response error: " + it.message.toString())
//            })
//        compositeDisposable?.add(castDisposable)
//        return castLD
//    }
//
//    override fun getSearchResult(query: String): MutableLiveData<List<Movie>> {
//        compositeDisposable = CompositeDisposable()
//        val searchDisposable = apiService.searchMovie(query = query)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe({
//                searchMoviesLD.value = it.movies
//            }, {
//                errorLD.value = it.message
//                Log.d("TAG", "getSearchResult Response error: " + it.message.toString())
//            })
//        compositeDisposable?.add(searchDisposable)
//        return searchMoviesLD
//    }


//    fun disposeDisposable() {
//        compositeDisposable?.dispose()
//    }
}