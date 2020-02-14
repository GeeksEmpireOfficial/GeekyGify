/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 3:50 PM
 * Last modified 2/13/20 2:55 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.Util

import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.Data.GifUserProfile

interface RecyclerViewGifFavoriteItemPress {
    fun itemPressed(gifUserProfile: GifUserProfile?, gifOriginalUri: String, linkToGif: String, gifPreviewUri: String)
}