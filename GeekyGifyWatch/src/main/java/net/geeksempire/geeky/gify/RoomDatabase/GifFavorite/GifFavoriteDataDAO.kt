/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/9/20 6:28 PM
 * Last modified 2/9/20 6:28 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.RoomDatabase.GifFavorite

import androidx.room.*

@Dao
interface FavoriteDataDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllNewFavoriteData(arrayOfFavoriteDataModels: List<FavoriteDataModel>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewFavoriteData(vararg arrayOfFavoriteDataModels: FavoriteDataModel)


    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateFavoriteData(vararg arrayOfFavoriteDataModels: FavoriteDataModel)

    @Delete
    suspend fun delete(favoriteDataModel: FavoriteDataModel)

    @Query("SELECT * FROM GifFavoriteDatabase ORDER BY TimeOrder ASC")
    suspend fun getAllFavoriteGif(): List<FavoriteDataModel>
}
