/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/24/20 8:43 PM
 * Last modified 2/24/20 8:06 PM
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
        val gifFavoriteDataInterface = GifFavoriteDatabase(context)
            .initialGifFavoriteDatabase().initDataAccessObject()

        return (context.getDatabasePath(DatabaseInformation.GIF_FAVORITE_DATABASE_NAME).exists() && (gifFavoriteDataInterface.getRowCount() > 0))
    }

    suspend fun favoriteDatabaseCount() : Int {
        val gifFavoriteDataInterface = GifFavoriteDatabase(context)
            .initialGifFavoriteDatabase().initDataAccessObject()

        return gifFavoriteDataInterface.getRowCount()
    }

    fun checkIfFavorite(likeButton: LikeButton, gifUrl: String) = CoroutineScope (
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