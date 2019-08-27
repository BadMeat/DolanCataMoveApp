package com.dolan.dolancatamoveapp.tv


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
import kotlinx.android.synthetic.main.fragment_tv.*

class TvFragment : Fragment() {

    private val itemList: MutableList<ResultsItem> = mutableListOf()
    private lateinit var viewModel: TvViewModel
    private lateinit var adapter: TvAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tv, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        adapter = TvAdapter(itemList) {
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(DetailActivity.EXTRA_TYPE, 1)
            intent.putExtra(DetailActivity.EXTRA_TV, it.id)
            startActivity(intent)
        }
        recycler_view.layoutManager = GridLayoutManager(context, 2)
        recycler_view.adapter = adapter

        viewModel = ViewModelProviders.of(this).get(TvViewModel::class.java)
        viewModel.getTv().observe(this, getTv)
        viewModel.setData()
    }

    private val getTv = Observer<MutableList<ResultsItem>> { t ->
        if (t != null) {
            itemList.addAll(t)
            adapter.notifyDataSetChanged()
            progress_bar.invisible()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        viewModel.onDestroy()
    }

}
