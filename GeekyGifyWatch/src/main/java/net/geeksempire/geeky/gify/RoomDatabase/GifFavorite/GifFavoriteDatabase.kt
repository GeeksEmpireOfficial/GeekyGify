/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/11/20 11:17 AM
 * Last modified 2/11/20 10:15 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.RoomDatabase.GifFavorite

import android.content.Context
import androidx.room.Room
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation

class GifFavoriteDatabase (var context: Context) {

    fun initialGifFavoriteDatabase() : FavoriteDataInterface {
        return Room.databaseBuilder(context, FavoriteDataInterface::class.java, DatabaseInformation.GIF_FAVORITE_DATABASE_NAME).build()
    }
}