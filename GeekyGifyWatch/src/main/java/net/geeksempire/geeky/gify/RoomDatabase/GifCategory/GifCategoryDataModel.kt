/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 7:43 PM
 * Last modified 2/10/20 7:32 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.RoomDatabase.GifCategory

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation

@Entity(tableName = DatabaseInformation.GIF_CATEGORY_DATABASE_NAME)
data class GifCategoryDataModel (
    @NonNull @PrimaryKey var CategoryName: String,

    @NonNull @ColumnInfo(name = "TimeOrder") var TimeOrder: Long
)