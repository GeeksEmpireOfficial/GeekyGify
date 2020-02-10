/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/9/20 6:28 PM
 * Last modified 2/9/20 6:14 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.RoomDatabase.GifCategory

import androidx.room.*

@Dao
interface GifCategoryDataDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertAllNewGifCategoryData(gifCategoryDataModels: List<GifCategoryDataModel>)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertNewGifCategoryData(vararg gifCategoryDataModels: GifCategoryDataModel)

    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun updateGifCategoryData(vararg gifCategoryDataModels: GifCategoryDataModel)

    @Delete
    suspend fun delete(categoryDataModel: GifCategoryDataModel)

    @Query("SELECT * FROM GifCategoryDatabase ORDER BY TimeOrder ASC")
    suspend fun getAllGifCategoryData(): List<GifCategoryDataModel>

    @Query("SELECT CategoryName FROM GifCategoryDatabase ORDER BY TimeOrder ASC")
    suspend fun getAllGifCategoryNames(): List<String>
}
