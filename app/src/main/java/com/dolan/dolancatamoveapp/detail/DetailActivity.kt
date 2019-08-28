package com.dolan.dolancatamoveapp.detail

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.dolan.dolancatamoveapp.BuildConfig
import com.dolan.dolancatamoveapp.R
import com.dolan.dolancatamoveapp.favorite.Favorite
import com.dolan.dolancatamoveapp.favorite.database
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.insert

class DetailActivity : AppCompatActivity() {

    private lateinit var tvViewModel: DetailTvViewModel
    private var itemList: DetailTvResponse? = null

    private lateinit var movieViewModel: DetailMovieViewModel
    private var itemMovie: DetailMovieResponse? = null

    companion object {
        const val EXTRA_ID = "extraId"
        const val EXTRA_TYPE = "extraType"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_favorite -> {
                insertTv()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        val id = intent.getIntExtra(EXTRA_ID, 0)
        when (val type: Int = intent.getIntExtra(EXTRA_TYPE, 0)) {
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

    private fun insertTv() {
        database.use {
            insert(
                Favorite.TABLE_NAME,
                Favorite.FAV_NAME to itemList?.name,
                Favorite.FAV_RATE to itemList?.voteAverage,
                Favorite.FAV_DETAIL to itemList?.overview,
                Favorite.FAV_POSTER to "${BuildConfig.BASE_IMAGE}${itemList?.posterPath}"
            )
        }
        Toast.makeText(this, "Data Berhasil Ditambah", Toast.LENGTH_SHORT).show()
    }
}
