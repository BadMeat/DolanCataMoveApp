package com.dolan.dolancatamoveapp.movie

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolan.dolancatamoveapp.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class MovieViewModel : ViewModel() {

    val listItem: MutableLiveData<MutableList<Movie>> = MutableLiveData()
    private var disposable: Disposable? = null

    fun setMovie() {
        val item: MutableList<Movie> = mutableListOf()
        disposable = ApiClient.instance.getMovieData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()?.results
            }
            .doFinally {
                listItem.postValue(item)
            }
            .subscribe(
                { result ->
                    if (result != null) {
                        for (e: Movie? in result) {
                            if (e != null) {
                                item.add(e)
                            }
                        }
                    }
                },
                { error -> Log.e("Error", "$error") }
            )
    }
}