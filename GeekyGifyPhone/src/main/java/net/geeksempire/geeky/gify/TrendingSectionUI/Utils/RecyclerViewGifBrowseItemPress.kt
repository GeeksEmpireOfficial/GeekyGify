/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 4:54 AM
 * Last modified 3/3/20 4:29 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Utils

import net.geeksempire.geeky.gify.ServerConnection.ViewModel.GifUserProfile


interface RecyclerViewGifBrowseItemPress {
    fun itemPressed(gifUserProfile: GifUserProfile?, gifOriginalUri: String, linkToGif: String, gifPreviewUri: String)
}