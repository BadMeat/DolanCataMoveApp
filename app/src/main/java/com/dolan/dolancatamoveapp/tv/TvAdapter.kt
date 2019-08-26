package com.dolan.dolancatamoveapp.tv

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.dolan.dolancatamoveapp.R

class TvAdapter(private val itemList: List<ResultsItem>) :
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
        holder.bindItem(itemList[position])
    }

    class TvHolder(view: View) : RecyclerView.ViewHolder(view) {

        private val textTitle: TextView = view.findViewById(R.id.txt_title)
        private val textDetail: TextView = view.findViewById(R.id.txt_detail)

        fun bindItem(e: ResultsItem) {
            textTitle.text = e.name
            textDetail.text = e.overview
        }
    }
}