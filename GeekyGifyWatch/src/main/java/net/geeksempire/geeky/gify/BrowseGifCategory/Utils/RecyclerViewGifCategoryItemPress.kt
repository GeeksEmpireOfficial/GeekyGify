/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/24/20 8:43 PM
 * Last modified 2/24/20 8:15 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Utils

interface RecyclerViewGifCategoryItemPress {
    fun itemPressed(rightLeft: Boolean, categoryName: String, viewType: String)
    fun itemPressed(rightLeft: Boolean, viewType: String)

    fun itemLongPressed(rightLeft: Boolean, categoryName: String, viewType: String)

    suspend fun deleteCategory(rightLeft: Boolean, itemPosition: Int, categoryName: String)
}