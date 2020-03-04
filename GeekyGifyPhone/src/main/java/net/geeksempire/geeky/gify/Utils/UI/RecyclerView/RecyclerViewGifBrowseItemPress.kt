/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 9:55 AM
 * Last modified 3/4/20 7:11 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Utils

import net.geeksempire.geeky.gify.ViewModel.GifUserProfile


interface RecyclerViewGifBrowseItemPress {
    fun itemPressed(gifUserProfile: GifUserProfile?, gifOriginalUri: String, linkToGif: String, gifPreviewUri: String)
}