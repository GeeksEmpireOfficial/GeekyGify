/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/11/20 11:17 AM
 * Last modified 2/11/20 11:17 AM
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

    fun addFavoriteGifDatabase(gifUrl: String,
                               gifUsername: String?, gifUserAvatar: String?, gifUserVerified: Boolean?)
            = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val gifFavoriteDataInterface = GifFavoriteDatabase(context).initialGifFavoriteDatabase()

        val favoriteDataModel = FavoriteDataModel(System.currentTimeMillis(), gifUrl,
            gifUsername.toString(), gifUserAvatar.toString(), gifUserVerified?:false,
            true)

        gifFavoriteDataInterface.initDataAccessObject().insertNewFavoriteData(favoriteDataModel)

        gifFavoriteDataInterface.close()
    }
}