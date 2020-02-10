/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/9/20 6:28 PM
 * Last modified 2/9/20 6:08 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.RoomDatabase.GifCategory

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseNames

@Entity(tableName = DatabaseNames.GIF_CATEGORY_DATABASE_NAME)
data class GifCategoryDataModel (
    @NonNull @PrimaryKey var CategoryName: String,

    @NonNull @ColumnInfo(name = "TimeOrder") var TimeOrder: Long
)