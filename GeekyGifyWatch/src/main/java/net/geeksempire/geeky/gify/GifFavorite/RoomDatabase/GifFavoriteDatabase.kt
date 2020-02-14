/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 5:20 PM
 * Last modified 2/13/20 5:19 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.RoomDatabase

import android.content.Context
import androidx.room.Room
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation

class GifFavoriteDatabase (var context: Context) {

    fun initialGifFavoriteDatabase() : FavoriteDataInterface {
        return Room.databaseBuilder(context, FavoriteDataInterface::class.java, DatabaseInformation.GIF_FAVORITE_DATABASE_NAME).build()
    }
}