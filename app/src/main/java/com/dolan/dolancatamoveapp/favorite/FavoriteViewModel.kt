package com.dolan.dolancatamoveapp.favorite

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.select
import java.lang.ref.WeakReference

class FavoriteViewModel : ViewModel() {

    private val favList: MutableLiveData<MutableList<Favorite>> = MutableLiveData()

    fun setFav(ctx: WeakReference<Context?>) {
        val context = ctx.get()
        val itemList: MutableList<Favorite> = mutableListOf()
        context?.database?.use {
            val result = select(Favorite.TABLE_NAME)
            val item = result.parseList(classParser<Favorite>())
            for (i: Favorite in item) {
                itemList.add(i)
            }
        }
        favList.postValue(itemList)
    }

    fun getFavList(): MutableLiveData<MutableList<Favorite>> = favList
}