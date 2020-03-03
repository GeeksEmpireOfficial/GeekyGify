/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 2:00 AM
 * Last modified 3/3/20 1:52 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.ServerConnection.ViewModel

data class GifUserProfile (var userName: String, var userAvatarUrl: String, var isUserVerified: Boolean)
data class BrowseGifItemData(var linkToGif: String,
                             var gifPreviewUrl: String, var gifOriginalUri: String,
                             var gifUserProfile: GifUserProfile?,
                             var backgroundColor: String)