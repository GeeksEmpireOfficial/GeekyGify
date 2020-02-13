/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 2:45 PM
 * Last modified 2/13/20 2:45 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.ViewModel

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.GifFavorite.UI.Adapter.Data.FavoritesGifItemData
import net.geeksempire.geeky.gify.RoomDatabase.GifFavorite.GifFavoriteDatabase

class FavoritesGifViewModel (val context: Context) : ViewModel() {

    val favoritesGifItemData: MutableLiveData<ArrayList<FavoritesGifItemData>> by lazy {
        MutableLiveData<ArrayList<FavoritesGifItemData>>()
    }

    fun setupGifsFavoritesData() = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

        try {
            val gifFavoritesItemData = GifFavoriteDatabase(context)
                .initialGifFavoriteDatabase().initDataAccessObject().getAllFavoriteGif() as ArrayList<FavoritesGifItemData>

            favoritesGifItemData.postValue(gifFavoritesItemData)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }
}