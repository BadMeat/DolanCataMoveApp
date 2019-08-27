package com.dolan.dolancatamoveapp

import com.dolan.dolancatamoveapp.detail.DetailResponse
import com.dolan.dolancatamoveapp.movie.MovieResponse
import com.dolan.dolancatamoveapp.tv.TvResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetDataService {

    @GET("discover/tv")
    fun getTv(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US"
    ): Observable<Response<TvResponse>>

    @GET("tv/{id}")
    fun getDetail(
        @Path(value = "id", encoded = true) id: Int,
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US"
    ): Observable<Response<DetailResponse>>

    @GET("discover/movie")
    fun getMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US"
    ): Observable<Response<MovieResponse>>
}