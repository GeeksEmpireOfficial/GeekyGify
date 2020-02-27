/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/26/20 9:34 PM
 * Last modified 2/26/20 9:29 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data

sealed class RecyclerViewRightLeftItem {
    object RightItem : RecyclerViewRightLeftItem()
    object LeftItem : RecyclerViewRightLeftItem()
}