/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/26/20 9:17 PM
 * Last modified 2/26/20 9:03 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Utils

interface RecyclerViewGifCategoryItemPress {
    fun itemPressed(rightLeft: Boolean, categoryName: String?, viewType: String)

    fun itemLongPressed(rightLeft: Boolean, categoryName: String?, viewType: String)

    suspend fun deleteCategory(rightLeft: Boolean, itemPosition: Int, categoryName: String)
}