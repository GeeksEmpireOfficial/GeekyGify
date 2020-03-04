/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 10:48 AM
 * Last modified 3/4/20 10:21 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Utils

import net.geeksempire.geeky.gify.ViewModel.GifUserProfile
import java.io.File


interface RecyclerViewGifBrowseItemPress {
    fun itemPressedTrending(gifUserProfile: GifUserProfile?, gifOriginalUri: String, linkToGif: String, gifPreviewUri: String) {}
    fun itemPressedCollection(gifDrawable: File, gifId: String) {}
}