/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 7:43 PM
 * Last modified 2/10/20 7:32 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.Util

import android.content.Context
import androidx.room.Room
import com.like.LikeButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation
import net.geeksempire.geeky.gify.RoomDatabase.GifFavorite.FavoriteDataInterface

class FavoriteCheckpoint {

    fun checkIfFavorite(context: Context, likeButton: LikeButton, gifUrl: String) = CoroutineScope(
        Dispatchers.IO).launch {

        if (context.getDatabasePath(DatabaseInformation.GIF_FAVORITE_DATABASE_NAME).exists()) {

            val gifFavoriteDataInterface = Room.databaseBuilder(context, FavoriteDataInterface::class.java, DatabaseInformation.GIF_FAVORITE_DATABASE_NAME)
                .build()

            val gifFavorited = gifFavoriteDataInterface
                .initDataAccessObject()
                .getFavoriteGif(gifUrl)?.GifFavorited ?: false

            withContext(Dispatchers.Main) {

                likeButton.isLiked = gifFavorited
            }

            gifFavoriteDataInterface.close()
        }
    }
}