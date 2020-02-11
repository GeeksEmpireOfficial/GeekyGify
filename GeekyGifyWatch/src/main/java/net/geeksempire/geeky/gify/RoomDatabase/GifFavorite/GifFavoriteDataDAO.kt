/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 5:18 PM
 * Last modified 2/10/20 4:48 PM
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

    @Query("SELECT * FROM GifFavoriteDatabase WHERE GifUrl = (:GifUrl)")
    suspend fun getFavoriteGif(GifUrl: String): FavoriteDataModel?
}
