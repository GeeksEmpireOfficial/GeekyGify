/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 10:33 AM
 * Last modified 2/13/20 10:33 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryAdapter
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryListViewHolder
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseFavoriteListViewHolder
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseSearchListViewHolder
import net.geeksempire.geeky.gify.R

class BrowseGifCategoryType {

    companion object {
        const val GIF_ITEM_SEARCH = 0
        const val GIF_ITEM_FAVORITE = 1
        const val GIF_ITEM_CATEGORIES = 2
    }
}

fun browseGifCategoryType(position: Int) : Int {

    return when (position) {
        BrowseGifCategoryType.GIF_ITEM_SEARCH -> {
            BrowseGifCategoryType.GIF_ITEM_SEARCH
        }
        BrowseGifCategoryType.GIF_ITEM_FAVORITE -> {
            BrowseGifCategoryType.GIF_ITEM_FAVORITE
        }
        BrowseGifCategoryType.GIF_ITEM_CATEGORIES -> {
            BrowseGifCategoryType.GIF_ITEM_CATEGORIES
        }
        else -> {
            BrowseGifCategoryType.GIF_ITEM_CATEGORIES
        }
    }
}

fun BrowseCategoryAdapter.browseGifCategoryTypeView(parentViewGroup: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {

    return when (browseGifCategoryType(viewType)) {
        BrowseGifCategoryType.GIF_ITEM_SEARCH -> {

            BrowseSearchListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_search_item_view,
                        parentViewGroup, false))

        }
        BrowseGifCategoryType.GIF_ITEM_FAVORITE -> {

            BrowseFavoriteListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_favorite_item_view,
                        parentViewGroup, false))

        }
        BrowseGifCategoryType.GIF_ITEM_CATEGORIES -> {

            BrowseCategoryListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_item_view,
                        parentViewGroup, false))

        }
        else -> {

            BrowseCategoryListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_item_view,
                        parentViewGroup, false))

        }
    }
}