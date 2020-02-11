/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 6:19 PM
 * Last modified 2/10/20 6:10 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.Util

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseNames
import net.geeksempire.geeky.gify.RoomDatabase.GifFavorite.FavoriteDataInterface
import net.geeksempire.geeky.gify.RoomDatabase.GifFavorite.FavoriteDataModel

class FavoriteIt {

    fun addFavoriteGifDatabase(context: Context,
                               gifUrl: String,
                               gifUsername: String?, gifUserAvatar: String?, gifUserVerified: Boolean?)
            = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val gifFavoriteDataInterface = Room.databaseBuilder(context, FavoriteDataInterface::class.java, DatabaseNames.GIF_FAVORITE_DATABASE_NAME)
            .build()

        val favoriteDataModel = FavoriteDataModel(System.currentTimeMillis(), gifUrl,
            gifUsername.toString(), gifUserAvatar.toString(), gifUserVerified?:false,
            true)

        gifFavoriteDataInterface.initDataAccessObject().insertNewFavoriteData(favoriteDataModel)

        gifFavoriteDataInterface.close()
    }
}