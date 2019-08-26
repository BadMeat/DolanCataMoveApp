package com.dolan.dolancatamoveapp.tv

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.dolan.dolancatamoveapp.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TvViewModel : ViewModel() {

    private val itemList: MutableLiveData<MutableList<ResultsItem>> = MutableLiveData()

    private var disposable: Disposable? = null

    fun setData() {
        val itemData: MutableList<ResultsItem> = mutableListOf()
        disposable = ApiClient.instance.getTvData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()?.results
            }
            .subscribe(
                { result ->
                    Log.d("Result", "$result")
                    if (result != null) {
                        for (a: ResultsItem? in result) {
                            itemData.add(a!!)
                        }
                    }
                    itemList.postValue(itemData)
                },
                { error -> Log.e("Error", "$error") }
            )
    }

    fun onDestroy() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }

    fun getTv(): MutableLiveData<MutableList<ResultsItem>> = itemList

}