/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 1:39 PM
 * Last modified 2/13/20 1:22 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import net.geeksempire.geeky.gify.GifFavorite.UI.Adapter.Data.FavoritesGifItemData

class FavoritesGifViewModel : ViewModel() {

    val favoritesGifItemData: MutableLiveData<ArrayList<FavoritesGifItemData>> by lazy {
        MutableLiveData<ArrayList<FavoritesGifItemData>>()
    }
}