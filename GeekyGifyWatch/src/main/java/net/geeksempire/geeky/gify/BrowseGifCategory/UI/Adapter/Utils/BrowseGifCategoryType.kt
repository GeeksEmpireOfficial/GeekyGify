/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 5:20 PM
 * Last modified 2/13/20 5:20 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryAdapter
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders.BrowseAddListViewHolder
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders.BrowseCategoryListViewHolder
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders.BrowseFavoriteListViewHolder
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders.BrowseSearchListViewHolder
import net.geeksempire.geeky.gify.R

class BrowseGifCategoryType {

    companion object {
        const val GIF_ITEM_SEARCH = "Search"
        const val GIF_ITEM_FAVORITE = "Favorites"
        const val GIF_ITEM_CATEGORIES = "Category"
        const val GIF_ITEM_CATEGORIES_ADD = "Add New Category"

        const val GIF_ITEM_SEARCH_TYPE = 0
        const val GIF_ITEM_FAVORITE_TYPE = 1
        const val GIF_ITEM_CATEGORIES_TYPE = 2
        const val GIF_ITEM_CATEGORIES_ADD_TYPE = 3
    }
}

fun browseGifCategoryType(positionTitle: String) : Int {

    return when (positionTitle) {
        BrowseGifCategoryType.GIF_ITEM_SEARCH -> {
            BrowseGifCategoryType.GIF_ITEM_SEARCH_TYPE
        }
        BrowseGifCategoryType.GIF_ITEM_FAVORITE -> {
            BrowseGifCategoryType.GIF_ITEM_FAVORITE_TYPE
        }
        BrowseGifCategoryType.GIF_ITEM_CATEGORIES -> {
            BrowseGifCategoryType.GIF_ITEM_CATEGORIES_TYPE
        }
        BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD -> {
            BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_TYPE
        }
        else -> {
            BrowseGifCategoryType.GIF_ITEM_CATEGORIES_TYPE
        }
    }
}

fun BrowseCategoryAdapter.browseGifCategoryTypeView(parentViewGroup: ViewGroup, viewType: String) : RecyclerView.ViewHolder {

    return when (browseGifCategoryType(viewType)) {
        BrowseGifCategoryType.GIF_ITEM_SEARCH_TYPE -> {

            BrowseSearchListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_search_item_view,
                        parentViewGroup, false
                    )
            )

        }
        BrowseGifCategoryType.GIF_ITEM_FAVORITE_TYPE -> {

            BrowseFavoriteListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_favorite_item_view,
                        parentViewGroup, false
                    )
            )

        }
        BrowseGifCategoryType.GIF_ITEM_CATEGORIES_TYPE -> {

            BrowseCategoryListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_item_view,
                        parentViewGroup, false
                    )
            )

        }
        BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_TYPE -> {

            BrowseAddListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_add_item_view,
                        parentViewGroup, false
                    )
            )

        }
        else -> {

            BrowseCategoryListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_item_view,
                        parentViewGroup, false
                    )
            )

        }
    }
}