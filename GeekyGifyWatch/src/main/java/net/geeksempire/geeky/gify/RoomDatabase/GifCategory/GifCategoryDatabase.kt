/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/11/20 11:17 AM
 * Last modified 2/11/20 10:04 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.RoomDatabase.GifCategory

import android.content.Context
import androidx.room.Room
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation

class GifCategoryDatabase (var context: Context) {

    fun initialGifCategoryDatabase() : GifCategoryDataInterface {
        return Room.databaseBuilder(context, GifCategoryDataInterface::class.java, DatabaseInformation.GIF_CATEGORY_DATABASE_NAME).build()
    }
}