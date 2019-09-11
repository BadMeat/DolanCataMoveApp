package com.dolan.dolancatamoveapp.movie

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dolan.dolancatamoveapp.BuildConfig
import com.dolan.dolancatamoveapp.R
import com.dolan.dolancatamoveapp.convertDate
import com.squareup.picasso.Picasso

class MovieAdapter(private val movieList: List<Movie>, private val listener: (Movie) -> Unit) :
    RecyclerView.Adapter<MovieAdapter.MovieHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieHolder {
        return MovieHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = movieList.size

    override fun onBindViewHolder(holder: MovieHolder, position: Int) {
        holder.bindItem(movieList[position], listener)
    }

    class MovieHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val txtTitle: TextView = view.findViewById(R.id.txt_title)
        private val txtDate: TextView = view.findViewById(R.id.txt_date)
        private val imgPoster: ImageView = view.findViewById(R.id.img_poster)

        fun bindItem(e: Movie, listener: (Movie) -> Unit) {
            txtTitle.text = e.title
            txtDate.text = convertDate(e.releaseDate)
            Picasso.get().load("${BuildConfig.BASE_IMAGE}${e.posterPath}").into(imgPoster)
            itemView.setOnClickListener {
                listener(e)
            }
        }
    }
}