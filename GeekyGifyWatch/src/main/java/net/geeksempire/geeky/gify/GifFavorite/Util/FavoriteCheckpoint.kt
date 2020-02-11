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
import com.like.LikeButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseNames
import net.geeksempire.geeky.gify.RoomDatabase.GifFavorite.FavoriteDataInterface

class FavoriteCheckpoint {

    fun checkIfFavorite(context: Context, likeButton: LikeButton, gifUrl: String) = CoroutineScope(
        Dispatchers.IO).launch {

        if (context.getDatabasePath(DatabaseNames.GIF_FAVORITE_DATABASE_NAME).exists()) {

            val gifFavoriteDataInterface = Room.databaseBuilder(context, FavoriteDataInterface::class.java, DatabaseNames.GIF_FAVORITE_DATABASE_NAME)
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