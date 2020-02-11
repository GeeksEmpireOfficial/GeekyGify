/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 7:43 PM
 * Last modified 2/10/20 7:33 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.RoomDatabase.GifCategory

import androidx.room.Database
import androidx.room.RoomDatabase
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation

@Database(entities = [GifCategoryDataModel::class], version = DatabaseInformation.GIF_CATEGORY_DATABASE_VERSION, exportSchema = false)
abstract class GifCategoryDataInterface : RoomDatabase() {
    abstract fun initDataAccessObject(): GifCategoryDataDAO
}