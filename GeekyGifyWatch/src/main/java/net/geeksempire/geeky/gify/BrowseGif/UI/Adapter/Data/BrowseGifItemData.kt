/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/11/20 11:17 AM
 * Last modified 2/11/20 10:51 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.Data

data class GifUserProfile (var userName: String, var userAvatarUrl: String, var isUserVerified: Boolean)
data class BrowseGifItemData(var linkToGif: String,
    var gifPreviewUrl: String, var gifOriginalUri: String,
                              var gifUserProfile: GifUserProfile?,
                              var backgroundColor: String)