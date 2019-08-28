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

    private lateinit var tvViewModel: DetailTvViewModel
    private var itemList: DetailTvResponse? = null

    private lateinit var movieViewModel: DetailMovieViewModel
    private var itemMovie: DetailMovieResponse? = null

    companion object {
        const val EXTRA_ID = "extraId"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        when (val id: Int = intent.getIntExtra(EXTRA_ID, 0)) {
            1 -> {
                setTv(id)
            }
            2 -> {
                setMovie(id)
            }
        }

    }

    private val getItem = Observer<DetailTvResponse> {
        if (it != null) {
            itemList = it
            setUI(it.name, it.posterPath, it.voteAverage, it.overview)
        }
    }

    private val getMovie = Observer<DetailMovieResponse> {
        if (it != null) {
            itemMovie = it
            setUI(it.title, it.posterPath, it.voteAverage, it.overview)
        }
    }

    private fun setUI(title: String?, imageUrl: String?, rate: Double?, detail: String?) {
        txt_title.text = title
        Picasso.get().load("${BuildConfig.BASE_IMAGE}$imageUrl").into(img_poster)
        txt_rate.text = rate.toString()
        txt_detail.text = detail
    }

    private fun setTv(id: Int) {
        itemList = DetailTvResponse()
        tvViewModel = ViewModelProviders.of(this).get(DetailTvViewModel::class.java)
        tvViewModel.getItem().observe(this, getItem)
        tvViewModel.setData(id)
    }


    private fun setMovie(id: Int) {
        itemMovie = DetailMovieResponse()
        movieViewModel = ViewModelProviders.of(this).get(DetailMovieViewModel::class.java)
        movieViewModel.movie.observe(this, getMovie)
        movieViewModel.setMovie(id)
    }
}
