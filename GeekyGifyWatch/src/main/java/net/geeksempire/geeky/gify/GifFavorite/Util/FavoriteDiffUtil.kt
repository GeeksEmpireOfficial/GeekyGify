/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/5/20 9:00 AM
 * Last modified 3/5/20 8:53 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.Util

import androidx.recyclerview.widget.DiffUtil
import net.geeksempire.geeky.gify.GifFavorite.RoomDatabase.FavoriteDataModel

class FavoriteDiffUtil (
    private val oldFavoriteData: ArrayList<FavoriteDataModel>,
    private val newFavoriteData: ArrayList<FavoriteDataModel>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return (oldFavoriteData[oldItemPosition].GifUrl == newFavoriteData[newItemPosition].GifUrl)
    }

    override fun getOldListSize(): Int {

        return oldFavoriteData.size
    }

    override fun getNewListSize(): Int {

        return newFavoriteData.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldFavoriteGifItemData = oldFavoriteData[oldItemPosition]
        val newFavoriteGifItemData = newFavoriteData[newItemPosition]

        return (oldFavoriteGifItemData == newFavoriteGifItemData)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}