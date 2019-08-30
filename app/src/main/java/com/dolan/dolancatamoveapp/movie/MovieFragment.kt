package com.dolan.dolancatamoveapp.movie


import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dolan.dolancatamoveapp.R
import com.dolan.dolancatamoveapp.detail.DetailActivity
import com.dolan.dolancatamoveapp.invisible
import kotlinx.android.synthetic.main.fragment_movie.*

class MovieFragment : Fragment() {

    private lateinit var movieViewModel: MovieViewModel
    private val movieList: MutableList<Movie> = mutableListOf()
    private lateinit var movieAdapter: MovieAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_movie, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        movieAdapter = MovieAdapter(movieList) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_ID, it.id)
            intent.putExtra(DetailActivity.EXTRA_TYPE, 2)
            startActivity(intent)
        }
        recycler_view.layoutManager = GridLayoutManager(context, 2)
        recycler_view.adapter = movieAdapter

        movieViewModel = ViewModelProviders.of(this).get(MovieViewModel::class.java)
        movieViewModel.listItem.observe(this, getMovie)
        movieViewModel.setMovie()
    }

    private val getMovie = Observer<MutableList<Movie>> {
        if (it != null) {
            movieList.clear()
            movieList.addAll(it)
            movieAdapter.notifyDataSetChanged()
            progress_bar.invisible()
        }
    }
}
