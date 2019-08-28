package com.dolan.dolancatamoveapp.favorite


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import com.dolan.dolancatamoveapp.R
import com.dolan.dolancatamoveapp.invisible
import kotlinx.android.synthetic.main.fragment_favorite.*
import java.lang.ref.WeakReference

class FavoriteFragment : Fragment() {

    private val favList: MutableList<Favorite> = mutableListOf()
    private lateinit var favViewModel: FavoriteViewModel
    private lateinit var favAdapter: FavoriteAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_favorite, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        favAdapter = FavoriteAdapter(favList) {

        }
        recycler_view.layoutManager = GridLayoutManager(context, 2)
        recycler_view.adapter = favAdapter

        favViewModel = ViewModelProviders.of(this).get(FavoriteViewModel::class.java)
        favViewModel.getFavList().observe(this, getFavorite)
        val weak = WeakReference(context)
        favViewModel.setFav(weak)
    }

    private val getFavorite = Observer<MutableList<Favorite>> {
        if (it != null) {
            favList.addAll(it)
            favAdapter.notifyDataSetChanged()
            progress_bar.invisible()
        }
    }
}