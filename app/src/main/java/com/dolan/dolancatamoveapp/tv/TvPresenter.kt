package com.dolan.dolancatamoveapp.tv

import com.dolan.dolancatamoveapp.ApiClient
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers

class TvPresenter(
    private val view: TvView.MainView
) : TvView.PresenterView {

    private var disposable: Disposable? = null

    override fun getData() {
        disposable = ApiClient.instance.getTvData()
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeOn(Schedulers.io())
            .map {
                it.body()
            }
            .doOnSubscribe {
                view.loadStart()
            }
            .doFinally {
                view.loadEnd()
            }
            .subscribe(
                { result -> view.loadData(result) },
                { error -> view.loadError(error.toString()) }
            )
    }

    override fun onDestroy() {
        if (disposable != null) {
            disposable?.dispose()
        }
    }
}