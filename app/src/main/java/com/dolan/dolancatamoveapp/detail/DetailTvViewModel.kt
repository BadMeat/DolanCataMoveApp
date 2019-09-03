package com.dolan.dolancatamoveapp.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolan.dolancatamoveapp.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import java.util.*

class DetailTvViewModel : ViewModel() {

    private val itemListId: MutableLiveData<DetailTvResponse> = MutableLiveData()
    private val itemListUs: MutableLiveData<DetailTvResponse> = MutableLiveData()
    private var disposable: Disposable? = null
    private var disposableUs : Disposable? = null

    fun setTv(id: Int) {
        Log.d("Locale Language", "${Locale.getDefault()}")
        val language = Locale.getDefault().toString()
        var lang = "en-US"
        if (language.equals("in_ID", true)) {
            lang = "id"
        }
        var itemId: DetailTvResponse? = null
        var itemUs: DetailTvResponse? = null
        disposable = ApiClient.instance.getTvDetail(id, lang)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()
            }
            .doFinally {
                itemListId.postValue(itemId)
            }
            .subscribe(
                { result ->
                    itemId = result
                },
                { error ->
                    Log.e("Error", "$error")
                }
            )

//        disposableUs = ApiClient.instance.getTvDetail(id, "en-US")
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribeOn(Schedulers.io())
//            .map {
//                it.body()
//            }
//            .doFinally {
//                itemListUs.postValue(itemUs)
//            }
//            .subscribe(
//                { result -> itemUs = result },
//                { error -> Log.e("Error", "$error") }
//            )
    }

    fun getItemId(): MutableLiveData<DetailTvResponse> {
        return itemListId
    }

    fun getItemUs(): MutableLiveData<DetailTvResponse> {
        return itemListUs
    }
}