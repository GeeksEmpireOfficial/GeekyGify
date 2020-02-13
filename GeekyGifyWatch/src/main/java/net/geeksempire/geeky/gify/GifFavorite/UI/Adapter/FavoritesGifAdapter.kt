/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 2:51 PM
 * Last modified 2/13/20 2:49 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.UI.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.RoomDatabase.GifFavorite.FavoriteDataModel
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources

class FavoritesGifAdapter (var context: Context,
                           var browseGifItemData: List<FavoriteDataModel>) : RecyclerView.Adapter<FavoritesGifListViewHolder>() {

    val listOfColors = GetResources(context).getNeonColors()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesGifListViewHolder {

        return FavoritesGifListViewHolder(LayoutInflater.from(context).inflate(R.layout.browse_gif_item_view, parent, false))
    }

    override fun getItemCount(): Int {

        return browseGifItemData.size
    }

    override fun onBindViewHolder(favoritesGifListViewHolder: FavoritesGifListViewHolder, position: Int) {
        val backgroundColor = listOfColors.random()

        favoritesGifListViewHolder.mainView.setBackgroundColor(Color.parseColor(backgroundColor))

        Glide.with(context)
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(browseGifItemData[position].GifPreviewUrl)
            .into(favoritesGifListViewHolder.gifPreview)

        favoritesGifListViewHolder.gifPreview.setOnClickListener {

//            recyclerViewGifBrowseItemPress.itemPressed(
//                browseGifItemData[position].gifUserProfile,
//                browseGifItemData[position].gifOriginalUri,
//                browseGifItemData[position].linkToGif,
//                browseGifItemData[position].gifPreviewUrl
//            )

        }

        favoritesGifListViewHolder.gifPreview.setOnLongClickListener {

            false
        }

        listOfColors.remove(backgroundColor)
    }
}