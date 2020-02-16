/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/16/20 10:45 AM
 * Last modified 2/16/20 10:45 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Utils

interface RecyclerViewGifCategoryItemPress {
    fun itemPressed(rightLeft: Boolean, categoryName: String, viewType: Int)
    fun itemLongPressed(rightLeft: Boolean, categoryName: String, viewType: Int)

    suspend fun deleteCategory(rightLeft: Boolean, itemPosition: Int, categoryName: String)
}