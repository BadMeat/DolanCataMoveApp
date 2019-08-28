package com.dolan.dolancatamoveapp

import com.dolan.dolancatamoveapp.detail.DetailMovieResponse
import com.dolan.dolancatamoveapp.detail.DetailTvResponse
import com.dolan.dolancatamoveapp.movie.MovieResponse
import com.dolan.dolancatamoveapp.tv.TvResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GetDataService {

    @GET("discover/tv?api_key=${BuildConfig.API_KEY}")
    fun getTv(
        @Query("language") language: String = "en-US"
    ): Observable<Response<TvResponse>>

    @GET("tv/{id}?api_key=${BuildConfig.API_KEY}")
    fun getTvDetail(
        @Path(value = "id", encoded = true) id: Int,
        @Query("language") language: String = "en-US"
    ): Observable<Response<DetailTvResponse>>

    @GET("discover/movie?api_key=${BuildConfig.API_KEY}")
    fun getMovie(
        @Query("language") language: String = "en-US"
    ): Observable<Response<MovieResponse>>

    @GET("movie/{id}?api_key=${BuildConfig.API_KEY}")
    fun getMovieDetail(
        @Path(value = "id", encoded = true) id: Int,
        @Query("language") language: String = "en-US"
    ): Observable<Response<DetailMovieResponse>>
}