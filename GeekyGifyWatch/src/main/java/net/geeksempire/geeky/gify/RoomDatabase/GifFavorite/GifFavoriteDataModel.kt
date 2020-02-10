/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/9/20 6:28 PM
 * Last modified 2/9/20 5:18 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.RoomDatabase.GifFavorite

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseNames

@Entity(tableName = DatabaseNames.GIF_FAVORITE_DATABASE_NAME)
data class FavoriteDataModel (
    @NonNull @PrimaryKey var TimeOrder: Long,

    @NonNull @ColumnInfo(name = "GifUrl") var GifUrl: String,
    @ColumnInfo(name = "GifUsername") var GifUsername: String,
    @ColumnInfo(name = "GifUserAvatar") var GifUserAvatar: String,
    @ColumnInfo(name = "GifUserVerified") var GifUserVerified: Boolean
)