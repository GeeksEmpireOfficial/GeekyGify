/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 10:44 AM
 * Last modified 2/8/20 10:34 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.Data

data class GifUserProfile (var userDisplayName: String, var userAvatarUrl: String, var isUserVerified: Boolean)
data class BrowseGifItemData (var gifPreviewUrl: String, var gifOriginalUri: String,
                              var gifUserProfile: GifUserProfile?,
                              var backgroundColor: String)