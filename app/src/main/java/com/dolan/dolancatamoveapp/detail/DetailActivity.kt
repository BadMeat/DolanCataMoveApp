package com.dolan.dolancatamoveapp.detail

import android.os.Bundle
import android.util.Log
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
import com.dolan.dolancatamoveapp.invisible
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import org.jetbrains.anko.db.classParser
import org.jetbrains.anko.db.delete
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select
import java.util.*

class DetailActivity : AppCompatActivity() {

    private lateinit var tvViewModel: DetailTvViewModel
    private var itemTvListId: DetailTvResponse? = null
    private var itemTvListUs: DetailTvResponse? = null

    private lateinit var movieViewModel: DetailMovieViewModel
    private var itemMovie: DetailMovieResponse? = null
    private var type = 0
    private var id = 0

    private var isFavorite = false

    private lateinit var itemMenu: Menu

    companion object {
        const val EXTRA_ID = "extraId"
        const val EXTRA_TYPE = "extraType"
    }

    private lateinit var lang: String

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite, menu)
        if (menu != null) {
            itemMenu = menu
        }
        setIcon()
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            R.id.menu_favorite -> {
                when (type) {
                    1 -> {
                        insertTv(id)
                    }
                    2 -> {
                        insertMovie(id)
                    }
                    else -> Toast.makeText(
                        this,
                        resources.getString(R.string.kind),
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
            android.R.id.home -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)
        lang = Locale.getDefault().toString()
        id = intent.getIntExtra(EXTRA_ID, 0)
        type = intent.getIntExtra(EXTRA_TYPE, 0)
        loadData(id)
        when (type) {
            1 -> {
                setTv(id)
            }
            2 -> {
                setMovie(id)
            }
        }
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private val getTvId = Observer<DetailTvResponse> {
        if (it != null) {
            Log.d("Ini dari indo ", "$it")
            itemTvListId = it
            setUI(it.name, it.posterPath, it.voteAverage, it.overview)
            progress_bar.invisible()
        }
    }

    private val getTvUs = Observer<DetailTvResponse> {
        if (it != null) {
            Log.d("Ini dari US ", "$it")
            itemTvListUs = it
            setUI(it.name, it.posterPath, it.voteAverage, it.overview)
            progress_bar.invisible()
        }
    }

    private val getMovie = Observer<DetailMovieResponse> {
        if (it != null) {
            itemMovie = it
            setUI(it.title, it.posterPath, it.voteAverage, it.overview)
            progress_bar.invisible()
        }
    }

    private fun setUI(title: String?, imageUrl: String?, rate: Double?, detail: String?) {
        txt_title.text = title
        Picasso.get().load("${BuildConfig.BASE_IMAGE}$imageUrl").into(img_poster)
        txt_rate.text = rate.toString()
        txt_detail.text = detail
    }

    private fun setTv(id: Int) {
        itemTvListId = DetailTvResponse()
        tvViewModel = ViewModelProviders.of(this).get(DetailTvViewModel::class.java)
        val tvId = tvViewModel.getItemId()
        val tvUs = tvViewModel.getItemUs()
        Log.d("bahasa digunakan","$lang")
        if (lang.equals("en-US", true)) {
            tvUs.observe(this, getTvUs)
        } else {
            tvId.observe(this, getTvId)
        }
        tvViewModel.setTv(id)
    }


    private fun setMovie(id: Int) {
        itemMovie = DetailMovieResponse()
        movieViewModel = ViewModelProviders.of(this).get(DetailMovieViewModel::class.java)
        movieViewModel.movie.observe(this, getMovie)
        movieViewModel.setMovie(id)
    }

    private fun insertTv(id: Int) {
        if (isFavorite) {
            removeMovie(id)
        } else {
            try {
                database.use {
                    insert(
                        Favorite.TABLE_NAME,
                        Favorite.FAV_ID to itemTvListId?.id,
                        Favorite.FAV_NAME to itemTvListId?.name,
                        Favorite.FAV_RATE to itemTvListId?.voteAverage,
                        Favorite.FAV_DETAIL_ID to itemTvListId?.overview,
                        Favorite.FAV_POSTER to "${BuildConfig.BASE_IMAGE}${itemTvListId?.posterPath}",
                        Favorite.FAV_TYPE to type,
                        Favorite.FAV_DETAIL_US to itemTvListUs?.overview
                    )
                }
                isFavorite = true
                setIcon()
                Toast.makeText(this, resources.getString(R.string.save_success), Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                Log.e("Error", "$e")
            }
        }
    }

    private fun insertMovie(id: Int) {
        if (isFavorite) {
            removeMovie(id)
        } else {
            try {
                database.use {
                    insert(
                        Favorite.TABLE_NAME,
                        Favorite.FAV_ID to itemMovie?.id,
                        Favorite.FAV_NAME to itemMovie?.title,
                        Favorite.FAV_RATE to itemMovie?.voteAverage,
                        Favorite.FAV_DETAIL_US to itemMovie?.overview,
                        Favorite.FAV_POSTER to "${BuildConfig.BASE_IMAGE}${itemMovie?.posterPath}",
                        Favorite.FAV_TYPE to type
                    )
                }
                isFavorite = true
                setIcon()
                Toast.makeText(this, resources.getString(R.string.save_success), Toast.LENGTH_SHORT)
                    .show()
            } catch (e: Exception) {
                Log.e("error", "$e")
            }
        }
    }

    private fun removeMovie(id: Int) {
        try {
            database.use {
                delete(Favorite.TABLE_NAME, "${Favorite.FAV_ID} = {id}", "id" to id)
            }
            Toast.makeText(this, resources.getString(R.string.delete_success), Toast.LENGTH_SHORT)
                .show()
            isFavorite = false
            setIcon()
        } catch (e: Exception) {
            Log.e("error", "$e")
        }
    }

    private fun loadData(id: Int) {
        database.use {
            val result = select(Favorite.TABLE_NAME)
                .whereArgs(
                    "${Favorite.FAV_ID} = {id}",
                    "id" to id
                )
            val favorite = result.parseList(classParser<Favorite>())
            if (favorite.isNotEmpty()) {
                isFavorite = true
            }
        }
    }

    private fun setIcon() {
        if (isFavorite) {
            itemMenu.getItem(0).icon = resources.getDrawable(R.drawable.favorited, null)
        } else {
            itemMenu.getItem(0).icon = resources.getDrawable(R.drawable.favorite, null)
        }
    }
}
