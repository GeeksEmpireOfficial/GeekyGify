/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 5:20 PM
 * Last modified 2/13/20 5:20 PM
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
import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.Data.GifUserProfile
import net.geeksempire.geeky.gify.GifFavorite.RoomDatabase.FavoriteDataModel
import net.geeksempire.geeky.gify.GifFavorite.Util.RecyclerViewGifFavoriteItemPress
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources

class FavoritesGifAdapter (private var context: Context,
                           private var recyclerViewGifFavoriteItemPress: RecyclerViewGifFavoriteItemPress) : RecyclerView.Adapter<FavoritesGifListViewHolder>() {

    var favoriteGifItemData = ArrayList<FavoriteDataModel>()

    private val listOfColors = GetResources(context).getNeonColors()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoritesGifListViewHolder {

        return FavoritesGifListViewHolder(LayoutInflater.from(context).inflate(R.layout.browse_gif_item_view, parent, false))
    }

    override fun getItemCount(): Int {

        return favoriteGifItemData.size
    }

    override fun onBindViewHolder(favoritesGifListViewHolder: FavoritesGifListViewHolder, position: Int) {

        favoritesGifListViewHolder.mainView.setBackgroundColor(Color.parseColor(listOfColors.random()))

        Glide.with(context)
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(favoriteGifItemData[position].GifPreviewUrl)
            .into(favoritesGifListViewHolder.gifPreview)

        favoritesGifListViewHolder.gifPreview.setOnClickListener {

            recyclerViewGifFavoriteItemPress.itemPressed(
                if (favoriteGifItemData[position].GifUsername != "${null}") { GifUserProfile(favoriteGifItemData[position].GifUsername, favoriteGifItemData[position].GifUserAvatar, favoriteGifItemData[position].GifUserVerified) } else { null },
                favoriteGifItemData[position].GifUrl,
                favoriteGifItemData[position].GifUrl,
                favoriteGifItemData[position].GifPreviewUrl
            )

        }

        favoritesGifListViewHolder.gifPreview.setOnLongClickListener {

            false
        }
    }
}