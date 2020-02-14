/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 5:20 PM
 * Last modified 2/13/20 5:20 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.Util

import android.content.Context
import com.like.LikeButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import net.geeksempire.geeky.gify.GifFavorite.RoomDatabase.GifFavoriteDatabase
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation

class FavoriteCheckpoint (var context: Context) {

    suspend fun favoriteDatabaseExists () : Boolean {

        return if (context.getDatabasePath(DatabaseInformation.GIF_FAVORITE_DATABASE_NAME).exists()) {
            val gifFavoriteDataInterface = GifFavoriteDatabase(
                context
            )
                .initialGifFavoriteDatabase().initDataAccessObject()

            (gifFavoriteDataInterface.getRowCount() > 0)
        } else {
            false
        }
    }

    fun checkIfFavorite(likeButton: LikeButton, gifUrl: String) = CoroutineScope(
        Dispatchers.IO).launch {

        if (context.getDatabasePath(DatabaseInformation.GIF_FAVORITE_DATABASE_NAME).exists()) {

            val gifFavoriteDataInterface = GifFavoriteDatabase(
                context
            ).initialGifFavoriteDatabase()

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