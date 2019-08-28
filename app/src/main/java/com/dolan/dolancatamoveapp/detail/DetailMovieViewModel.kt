package com.dolan.dolancatamoveapp.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolan.dolancatamoveapp.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

/**
 * Created by Bencoleng on 28/08/2019.
 */
class DetailMovieViewModel : ViewModel() {

    val movie: MutableLiveData<DetailMovieResponse> = MutableLiveData()
    var disposable: Disposable? = null

    fun setMovie(id: Int) {
        var item: DetailMovieResponse? = null
        disposable = ApiClient.instance.getMovieDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()
            }
            .doFinally {
                movie.postValue(item)
            }
            .subscribe(
                { result ->
                    item = result
                },
                { error -> Log.e("erro", "$error") }
            )
    }
}