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

    private val itemList: MutableLiveData<DetailTvResponse> = MutableLiveData()
    private var disposable: Disposable? = null

    fun setData(id: Int) {
        Log.d("Locale Language", "${Locale.getDefault()}")
        val language = Locale.getDefault().toString()
        var lang = "en-US"
        if (language.equals("in_ID", true)) {
            lang = "id"
        }
        var item: DetailTvResponse? = null
        disposable = ApiClient.instance.getTvDetail(id, lang)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()
            }
            .doFinally {
                itemList.postValue(item)
            }
            .subscribe(
                { result ->
                    item = result
                },
                { error ->
                    Log.e("Error", "$error")
                }
            )
    }

    fun getItem(): MutableLiveData<DetailTvResponse> {
        return itemList
    }
}