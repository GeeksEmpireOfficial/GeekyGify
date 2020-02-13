/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 2:45 PM
 * Last modified 2/13/20 2:45 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.Extension

import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorites_gif_list_view.*
import net.geeksempire.geeky.gify.GifFavorite.UI.Adapter.FavoritesGifAdapter
import net.geeksempire.geeky.gify.GifFavorite.UI.FavoritesGifView
import net.geeksempire.geeky.gify.GifFavorite.ViewModel.FavoritesGifViewModel
import net.geeksempire.geeky.gify.GifFavorite.ViewModel.FavoritesGifViewModelFactory

fun FavoritesGifView.favoritesGifViewObserverExtension() : FavoritesGifViewModel {

    gifList.layoutManager = GridLayoutManager(applicationContext, 2, RecyclerView.VERTICAL, false)

    val favoritesGifViewModelFactory = FavoritesGifViewModelFactory(applicationContext)
    val favoritesGifViewModel = ViewModelProvider(this@favoritesGifViewObserverExtension, favoritesGifViewModelFactory).get(FavoritesGifViewModel::class.java)

    favoritesGifViewModel.favoritesGifItemData.observe(this@favoritesGifViewObserverExtension,
        Observer {
            if (it.size > 0) {
                gifList.visibility = View.VISIBLE
                progressBarGifs.hide()

                val browseGifAdapter = FavoritesGifAdapter(this@favoritesGifViewObserverExtension, it)

                gifList.adapter = browseGifAdapter
                browseGifAdapter.notifyDataSetChanged()

                nextGifPage.visibility = View.VISIBLE
            }

            Log.d(this@favoritesGifViewObserverExtension.javaClass.simpleName, "GifsFavoriteListData Observe")
        })

    favoritesGifViewModel.setupGifsFavoritesData()

    return favoritesGifViewModel
}