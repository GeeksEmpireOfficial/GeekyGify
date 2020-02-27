/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/26/20 9:34 PM
 * Last modified 2/26/20 9:25 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Utils

import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.RecyclerViewRightLeftItem

interface RecyclerViewGifCategoryItemPress {
    fun itemPressed(rightLeft: RecyclerViewRightLeftItem, categoryName: String?, viewType: String)

    fun itemLongPressed(rightLeft: RecyclerViewRightLeftItem, categoryName: String?, viewType: String)

    suspend fun deleteCategory(rightLeft: RecyclerViewRightLeftItem, itemPosition: Int, categoryName: String)
}