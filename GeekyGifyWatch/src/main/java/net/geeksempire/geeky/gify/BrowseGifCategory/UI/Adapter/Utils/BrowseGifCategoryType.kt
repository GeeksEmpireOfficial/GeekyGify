/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/2/20 12:21 AM
 * Last modified 3/1/20 11:49 PM
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
        const val GIF_ITEM_SEARCH = "Search-ItemType"
        /**
         * Favorites
         */
        const val GIF_ITEM_FAVORITE = "Favorites-ItemType"
        /**
         * Category
         */
        const val GIF_ITEM_CATEGORIES = "Category-ItemType"
        /**
         * Trending
         */
        const val GIF_ITEM_TRENDING = "Trending-ItemType"
        /**
         * Add New Category
         */
        const val GIF_ITEM_CATEGORIES_ADD_NEW = "AddNewCategory-ItemType"
        /**
         * Social Media
         */
        const val GIF_ITEM_SOCIAL_MEDIA = "SocialMedia-ItemType"
        /**
         * Null Item
         */
        const val GIF_ITEM_NULL = "NULL-ItemType"
    }
}

fun BrowseCategoryAdapter.browseGifCategoryTypeView(parentViewGroup: ViewGroup, viewType: String) : RecyclerView.ViewHolder {

    return when ((viewType)) {
        BrowseGifCategoryType.GIF_ITEM_SEARCH -> {

            BrowseSearchListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_search_item_view,
                        parentViewGroup, false
                    )
            )

        }
        BrowseGifCategoryType.GIF_ITEM_FAVORITE -> {

            BrowseFavoriteListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_favorite_item_view,
                        parentViewGroup, false
                    )
            )

        }
        BrowseGifCategoryType.GIF_ITEM_CATEGORIES -> {

            BrowseCategoryListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_item_view,
                        parentViewGroup, false
                    )
            )

        }
        BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW -> {

            BrowseAddListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_category_add_item_view,
                        parentViewGroup, false
                    )
            )

        }
        BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA -> {

            BrowseSocialMediaListViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_social_media_item_view,
                        parentViewGroup, false
                    )
            )
        }
        BrowseGifCategoryType.GIF_ITEM_NULL -> {

            BrowseEmptyViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_empty_item,
                        parentViewGroup, false
                    )
            )

        }
        else -> {

            BrowseEmptyViewHolder(
                LayoutInflater.from(context)
                    .inflate(
                        R.layout.browse_gif_empty_item,
                        parentViewGroup, false
                    )
            )
        }
    }
}