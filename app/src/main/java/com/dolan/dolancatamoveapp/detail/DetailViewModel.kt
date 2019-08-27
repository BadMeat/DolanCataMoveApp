package com.dolan.dolancatamoveapp.detail

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolan.dolancatamoveapp.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class DetailViewModel : ViewModel() {

    private val itemList: MutableLiveData<DetailResponse> = MutableLiveData()
    private var disposable: Disposable? = null

    fun setData(id: Int) {
        var item: DetailResponse? = null
        disposable = ApiClient.instance.getDetail(id)
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()
            }
            .doFinally {
                Log.d("Itemku cuy","$item")
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

    fun getItem(): MutableLiveData<DetailResponse> {
        return itemList
    }
}