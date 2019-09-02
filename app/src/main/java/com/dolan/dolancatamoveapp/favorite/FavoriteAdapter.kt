package com.dolan.dolancatamoveapp.favorite

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dolan.dolancatamoveapp.R
import com.squareup.picasso.Picasso
import java.util.*

class FavoriteAdapter(
    private val favList: List<Favorite>,
    private val listener: (Favorite) -> Unit
) : RecyclerView.Adapter<FavoriteAdapter.FavoriteHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteHolder {
        return FavoriteHolder(
            LayoutInflater.from(parent.context).inflate(
                R.layout.item_layout,
                parent,
                false
            )
        )
    }

    override fun getItemCount() = favList.size

    override fun onBindViewHolder(holder: FavoriteHolder, position: Int) {
        holder.bindItem(favList[position], listener)
    }

    class FavoriteHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val txtTitle: TextView = view.findViewById(R.id.txt_title)
        private val txtDetail: TextView = view.findViewById(R.id.txt_date)
        private val txtRate: TextView = view.findViewById(R.id.txt_rate)
        private val imgPoster: ImageView = view.findViewById(R.id.img_poster)

        private val lang = Locale.getDefault().toString()

        fun bindItem(e: Favorite, listener: (Favorite) -> Unit) {
            txtTitle.text = e.name
            if (lang.equals("in_ID", true)) {
                txtDetail.text = e.detail_id
            } else {
                txtDetail.text = e.detail_us
            }
            txtRate.text = e.rate.toString()
            Picasso.get().load(e.poster).into(imgPoster)
            itemView.setOnClickListener {
                listener(e)
            }
        }
    }
}