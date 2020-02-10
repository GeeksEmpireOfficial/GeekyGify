/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/9/20 6:28 PM
 * Last modified 2/9/20 5:15 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.RoomDatabase.GifCategory

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [GifCategoryDataModel::class], version = 100000, exportSchema = false)
abstract class GifCategoryDataInterface : RoomDatabase() {
    abstract fun initDataAccessObject(): GifCategoryDataDAO
}