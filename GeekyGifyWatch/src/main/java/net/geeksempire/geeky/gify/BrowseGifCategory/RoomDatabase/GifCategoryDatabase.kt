/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 5:20 PM
 * Last modified 2/13/20 5:19 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.RoomDatabase

import android.content.Context
import androidx.room.Room
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation

class GifCategoryDatabase (var context: Context) {

    fun initialGifCategoryDatabase() : GifCategoryDataInterface {
        return Room.databaseBuilder(context, GifCategoryDataInterface::class.java, DatabaseInformation.GIF_CATEGORY_DATABASE_NAME).build()
    }
}