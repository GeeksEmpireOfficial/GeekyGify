/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/18/20 6:56 PM
 * Last modified 2/18/20 6:43 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Utils

interface RecyclerViewGifCategoryItemPress {
    fun itemPressed(rightLeft: Boolean, categoryName: String, viewType: Int)
    fun itemPressed(rightLeft: Boolean, viewType: Int)

    fun itemLongPressed(rightLeft: Boolean, categoryName: String, viewType: Int)

    suspend fun deleteCategory(rightLeft: Boolean, itemPosition: Int, categoryName: String)
}