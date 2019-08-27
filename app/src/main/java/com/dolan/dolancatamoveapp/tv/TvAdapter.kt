package com.dolan.dolancatamoveapp.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dolan.dolancatamoveapp.BuildConfig
import com.dolan.dolancatamoveapp.R
import com.squareup.picasso.Picasso

class TvAdapter(
    private val itemList: List<ResultsItem>,
    private val listener: (ResultsItem) -> Unit
) :
    RecyclerView.Adapter<TvAdapter.TvHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TvHolder {
        return TvHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = itemList.size

    override fun onBindViewHolder(holder: TvHolder, position: Int) {
        holder.bindItem(itemList[position], listener)
    }

    class TvHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textTitle: TextView = view.findViewById(R.id.txt_title)
        private val textDate: TextView = view.findViewById(R.id.txt_date)
        private val imgPoster: ImageView = view.findViewById(R.id.img_poster)

        fun bindItem(e: ResultsItem, listener: (ResultsItem) -> Unit) {
            textTitle.text = e.name
            textDate.text = e.firstAirDate
            Picasso.get().load("${BuildConfig.BASE_IMAGE}${e.posterPath}").into(imgPoster)
            itemView.setOnClickListener {
                listener(e)
            }
        }
    }
}