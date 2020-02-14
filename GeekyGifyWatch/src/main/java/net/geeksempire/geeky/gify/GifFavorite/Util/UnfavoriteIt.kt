/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 5:20 PM
 * Last modified 2/13/20 5:19 PM
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
import net.geeksempire.geeky.gify.GifFavorite.RoomDatabase.GifFavoriteDatabase

class UnfavoriteIt (var context: Context) {

    fun removeFavoriteGifDatabase(gifUrl: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val gifFavoriteDataInterface = GifFavoriteDatabase(
            context
        ).initialGifFavoriteDatabase()

        gifFavoriteDataInterface.initDataAccessObject().getFavoriteGif(gifUrl)?.let {
            gifFavoriteDataInterface.initDataAccessObject().delete(it)
        }

        gifFavoriteDataInterface.close()
    }

}