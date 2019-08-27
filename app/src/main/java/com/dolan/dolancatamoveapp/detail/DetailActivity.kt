package com.dolan.dolancatamoveapp.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dolan.dolancatamoveapp.BuildConfig
import com.dolan.dolancatamoveapp.R
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity() {

    private lateinit var viewModel: DetailViewModel
    private var itemList: DetailResponse? = null

    companion object {
        const val EXTRA_TV = "extraTv"
        const val EXTRA_MOVIE = "extraMovie"
        const val EXTRA_FAV = "extraFav"
        const val EXTRA_TYPE = "extraType"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        var id = 0
        when (intent.getIntExtra(EXTRA_TYPE, 0)) {
            1 -> {
                id = intent.getIntExtra(EXTRA_TV, 0)
            }
            2 -> {
                id = intent.getIntExtra(EXTRA_MOVIE, 0)
            }
            3 -> {
                id = intent.getIntExtra(EXTRA_FAV, 0)
            }
        }
        itemList = DetailResponse()
        viewModel = ViewModelProviders.of(this).get(DetailViewModel::class.java)
        viewModel.getItem().observe(this, getItem)
        viewModel.setData(id)
    }

    private val getItem = Observer<DetailResponse> {
        if (it != null) {
            itemList = it
            setUI()
        }
    }

    private fun setUI() {
        txt_title.text = itemList?.name
        Picasso.get().load("${BuildConfig.BASE_IMAGE}${itemList?.posterPath}").into(img_poster)
        txt_rate.text = itemList?.voteAverage.toString()
        txt_detail.text = itemList?.overview
    }
}
