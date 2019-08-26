package com.dolan.dolancatamoveapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.dolan.dolancatamoveapp.tv.ResultsItem
import com.dolan.dolancatamoveapp.tv.TvAdapter
import com.dolan.dolancatamoveapp.tv.TvViewModel
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private val itemList: MutableList<ResultsItem> = mutableListOf()
    private lateinit var viewModel: TvViewModel
    private lateinit var adapter: TvAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        adapter = TvAdapter(itemList)
        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(TvViewModel::class.java)
        viewModel.getTv().observe(this, getTv)
        viewModel.setData()
    }

    private val getTv = Observer<MutableList<ResultsItem>> { t ->
        if (t != null) {
            itemList.addAll(t)
            adapter.notifyDataSetChanged()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }
}
