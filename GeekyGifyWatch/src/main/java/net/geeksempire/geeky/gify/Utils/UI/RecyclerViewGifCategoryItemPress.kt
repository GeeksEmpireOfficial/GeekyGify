/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 10:33 AM
 * Last modified 2/13/20 10:27 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.UI

interface RecyclerViewGifCategoryItemPress {
    fun itemPressed(rightLeft: Boolean, categoryName: String, viewType: Int)
    fun itemLongPressed(rightLeft: Boolean, categoryName: String, viewType: Int)
}