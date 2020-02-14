/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 5:48 PM
 * Last modified 2/13/20 5:20 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.RoomDatabase

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation

@Entity(tableName = DatabaseInformation.GIF_FAVORITE_DATABASE_NAME)
data class FavoriteDataModel (
    @NonNull @PrimaryKey var TimeOrder: Long,

    @NonNull @ColumnInfo(name = "GifUrl") var GifUrl: String,
    @NonNull @ColumnInfo(name = "GifPreviewUrl") var GifPreviewUrl: String,

    @ColumnInfo(name = "GifUsername") var GifUsername: String,
    @ColumnInfo(name = "GifUserAvatar") var GifUserAvatar: String,
    @ColumnInfo(name = "GifUserVerified") var GifUserVerified: Boolean,

    @ColumnInfo(name = "GifFavorited") var GifFavorited: Boolean
)