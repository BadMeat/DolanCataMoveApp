package com.dolan.dolancatamoveapp

import com.dolan.dolancatamoveapp.tv.TvResponse
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GetDataService {

    @GET("tv/on_the_air")
    fun getMovie(
        @Query("api_key") apiKey: String = BuildConfig.API_KEY,
        @Query("language") language: String = "en-US"
    ): Observable<Response<TvResponse>>
}