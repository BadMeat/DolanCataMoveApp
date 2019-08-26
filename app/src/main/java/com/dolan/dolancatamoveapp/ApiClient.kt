package com.dolan.dolancatamoveapp

import com.dolan.dolancatamoveapp.tv.TvResponse
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ApiClient {

    private val dataService: GetDataService

    companion object {
        private var apiClient: ApiClient? = null
        val instance: ApiClient
            get() {
                if (apiClient == null) {
                    apiClient = ApiClient()
                }
                return apiClient as ApiClient
            }
    }

    init {
        val retrofit = Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        dataService = retrofit.create(GetDataService::class.java)
    }

    fun getTvData(): Observable<Response<TvResponse>> {
        return dataService.getMovie()
    }
}