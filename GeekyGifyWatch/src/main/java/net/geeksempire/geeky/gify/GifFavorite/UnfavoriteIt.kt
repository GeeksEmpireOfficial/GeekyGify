/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 5:25 PM
 * Last modified 2/10/20 5:22 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseNames
import net.geeksempire.geeky.gify.RoomDatabase.GifFavorite.FavoriteDataInterface

class UnfavoriteIt {

    fun removeFavoriteGifDatabase(context: Context, gifUrl: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val gifFavoriteDataInterface = Room.databaseBuilder(context, FavoriteDataInterface::class.java, DatabaseNames.GIF_FAVORITE_DATABASE_NAME)
            .build()

        gifFavoriteDataInterface.initDataAccessObject().getFavoriteGif(gifUrl)?.let {
            gifFavoriteDataInterface.initDataAccessObject().delete(it)
        }

        gifFavoriteDataInterface.close()
    }

}