/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/9/20 6:28 PM
 * Last modified 2/9/20 5:16 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.RoomDatabase.GifFavorite

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(entities = [FavoriteDataModel::class], version = 100000, exportSchema = false)
abstract class FavoriteDataInterface : RoomDatabase() {
    abstract fun initDataAccessObject(): FavoriteDataDAO
}