package com.dolan.dolancatamoveapp.tv

interface TvView {
    interface MainView {
        fun loadData(data: TvResponse?)
        fun loadStart()
        fun loadEnd()
        fun loadError(error: String)
    }

    interface PresenterView {
        fun getData()
        fun onDestroy()
    }
}