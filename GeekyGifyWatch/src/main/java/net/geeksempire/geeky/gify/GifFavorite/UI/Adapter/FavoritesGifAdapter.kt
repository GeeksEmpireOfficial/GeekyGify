/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 1:39 PM
 * Last modified 2/13/20 1:22 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.UI.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.geeksempire.geeky.gify.GifFavorite.UI.Adapter.Data.FavoritesGifItemData
import net.geeksempire.geeky.gify.R

class FavoritesGifAdapter (var context: Context,
                           var browseGifItemData: ArrayList<FavoritesGifItemData>) : RecyclerView.Adapter<FavoritesGifListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesGifListViewHolder {

        return FavoritesGifListViewHolder(LayoutInflater.from(context).inflate(R.layout.browse_gif_item_view, parent, false))
    }

    override fun getItemCount(): Int {

        return browseGifItemData.size
    }

    override fun onBindViewHolder(favoritesGifListViewHolder: FavoritesGifListViewHolder, position: Int) {

    }
}