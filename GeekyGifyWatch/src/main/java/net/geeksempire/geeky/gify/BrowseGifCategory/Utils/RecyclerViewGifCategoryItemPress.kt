/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 8:48 PM
 * Last modified 2/13/20 8:26 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Utils

interface RecyclerViewGifCategoryItemPress {
    fun itemPressed(rightLeft: Boolean, categoryName: String, viewType: Int)
    fun itemLongPressed(rightLeft: Boolean, categoryName: String, viewType: Int)

    fun deleteCategory(rightLeft: Boolean, itemPosition: Int, categoryName: String)
}