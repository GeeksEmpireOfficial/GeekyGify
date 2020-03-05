/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/5/20 8:01 AM
 * Last modified 3/4/20 9:55 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.ViewModel

import java.io.File

data class GifUserProfile (var userName: String, var userAvatarUrl: String, var isUserVerified: Boolean)
data class BrowseTrendingGifItemData(var linkToGif: String,
                                     var gifPreviewUrl: String, var gifOriginalUri: String,
                                     var gifUserProfile: GifUserProfile?,
                                     var backgroundColor: String)

data class BrowseCollectionGifItemData(var gifDrawable: File,
                                       var gifId: String,
                                       var backgroundColor: String)