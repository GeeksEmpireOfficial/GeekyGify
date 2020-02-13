/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 1:39 PM
 * Last modified 2/13/20 1:37 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.Util

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import net.geeksempire.geeky.gify.RoomDatabase.GifFavorite.FavoriteDataModel
import net.geeksempire.geeky.gify.RoomDatabase.GifFavorite.GifFavoriteDatabase

class FavoriteIt (var context: Context) {

    fun addFavoriteGifDatabase(gifUrl: String, gifPreviewUrl: String,
                               gifUsername: String?, gifUserAvatar: String?, gifUserVerified: Boolean?)
            = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val gifFavoriteDataInterface = GifFavoriteDatabase(context).initialGifFavoriteDatabase()

        val favoriteDataModel = FavoriteDataModel(System.currentTimeMillis(), gifUrl, gifPreviewUrl,
            gifUsername.toString(), gifUserAvatar.toString(), gifUserVerified?:false,
            true)

        gifFavoriteDataInterface.initDataAccessObject().insertNewFavoriteData(favoriteDataModel)

        gifFavoriteDataInterface.close()
    }
}