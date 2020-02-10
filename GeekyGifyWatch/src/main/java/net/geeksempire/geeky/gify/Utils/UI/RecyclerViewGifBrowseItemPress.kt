/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 2:34 PM
 * Last modified 2/10/20 2:17 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.UI

import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.Data.GifUserProfile

interface RecyclerViewGifBrowseItemPress {
    fun itemPressed(gifUserProfile: GifUserProfile?, gifOriginalUri: String)
}