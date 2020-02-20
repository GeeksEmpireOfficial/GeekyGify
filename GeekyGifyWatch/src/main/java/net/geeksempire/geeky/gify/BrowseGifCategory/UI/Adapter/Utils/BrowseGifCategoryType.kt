/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/19/20 4:10 PM
 * Last modified 2/19/20 3:20 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryAdapter
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders.*
import net.geeksempire.geeky.gify.R

class BrowseGifCategoryType {

    companion object {
        /**
         * Search
         */
        const val GIF_ITEM_SEARCH = "Search"
        /**
         * Favorites
         */
        const val GIF_ITEM_FAVORITE = "Favorites"
        /**
         * Category
         */
        const val GIF_ITEM_CATEGORIES = "Category"
        /**
         * Add New Category
         */
        const val GIF_ITEM_CATEGORIES_ADD = "Add New Category"
        /**
         * Social Media
         */
        const val GIF_ITEM_SOCIAL_MEDIA = "Social Media"

        const val GIF_ITEM_SEARCH_TYPE = 0
        const val GIF_ITEM_FAVORITE_TYPE = 1
        const val GIF_ITEM_CATEGORIES_TYPE = 2
        const val GIF_ITEM_CATEGORIES_ADD_TYPE = 3
        const val GIF_ITEM_SOCIAL_MEDIA_TYPE = 4
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
        BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA -> {
            BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA_TYPE
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
        BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA_TYPE -> {

            BrowseSocialMediaListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_social_media_item_view,
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