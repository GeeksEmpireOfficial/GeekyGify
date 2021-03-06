/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 6/18/20 11:18 AM
 * Last modified 6/18/20 11:13 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.Extension

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.Data.GifUserProfile
import net.geeksempire.geeky.gify.GifFavorite.RoomDatabase.FavoriteDataModel
import net.geeksempire.geeky.gify.GifFavorite.UI.Adapter.FavoritesGifAdapter
import net.geeksempire.geeky.gify.GifFavorite.UI.FavoritesGifView
import net.geeksempire.geeky.gify.GifFavorite.Util.RecyclerViewGifFavoriteItemPress
import net.geeksempire.geeky.gify.GifFavorite.ViewModel.FavoritesGifViewModel
import net.geeksempire.geeky.gify.GifFavorite.ViewModel.FavoritesGifViewModelFactory
import net.geeksempire.geeky.gify.R

fun FavoritesGifView.favoritesGifViewObserverExtension() : FavoritesGifViewModel {

    favoritesGifListViewBinding.gifList.layoutManager = GridLayoutManager(applicationContext, 2, RecyclerView.VERTICAL, false)

    val favoritesGifViewModelFactory = FavoritesGifViewModelFactory(applicationContext)
    val favoritesGifViewModel = ViewModelProvider(this@favoritesGifViewObserverExtension, favoritesGifViewModelFactory).get(FavoritesGifViewModel::class.java)

    val recyclerViewGifFavoriteItemPress: RecyclerViewGifFavoriteItemPress = object :
        RecyclerViewGifFavoriteItemPress {
        override fun itemPressed(gifUserProfile: GifUserProfile?,
                                 gifOriginalUri: String, linkToGif: String, gifPreviewUri: String) {

            favoritesGifListViewBinding.fragmentPlaceHolder.visibility = View.VISIBLE

            gifViewer.arguments = Bundle().apply {
                putString(GiphyJsonDataStructure.DATA_URL, linkToGif)
                putString(GiphyJsonDataStructure.DATA_IMAGES_PREVIEW_GIF, gifPreviewUri)
                putString(GiphyJsonDataStructure.DATA_IMAGES_ORIGINAL, gifOriginalUri)

                gifUserProfile?.let { gifUserProfile ->
                    putString(GiphyJsonDataStructure.DATA_USER_NAME, gifUserProfile.userName)
                    putString(GiphyJsonDataStructure.DATA_USER_AVATAR_URL, gifUserProfile.userAvatarUrl)
                    putBoolean(GiphyJsonDataStructure.DATA_USER_IS_VERIFIED, gifUserProfile.isUserVerified)
                }
            }

            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.slide_from_right, 0)
                .replace(R.id.fragmentPlaceHolder, gifViewer, "GIF VIEWER")
                .commit()

        }
    }

    var favoritesGifAdapter: FavoritesGifAdapter? = null

    favoritesGifViewModel.favoritesGifItemData.observe(this@favoritesGifViewObserverExtension,
        Observer {
            if (it.isNotEmpty()) {
                if (favoritesGifAdapter == null) {

                    favoritesGifListViewBinding.gifList.visibility = View.VISIBLE
                    favoritesGifListViewBinding.progressBarGifs.hide()

                    favoritesGifAdapter = FavoritesGifAdapter(this@favoritesGifViewObserverExtension, recyclerViewGifFavoriteItemPress)
                    favoritesGifAdapter?.favoriteGifItemData = it as ArrayList<FavoriteDataModel>

                    favoritesGifListViewBinding.gifList.adapter = favoritesGifAdapter
                    favoritesGifAdapter?.notifyDataSetChanged()

                } else {

                    favoritesGifAdapter?.updateCollectionData(it as ArrayList<FavoriteDataModel>)

                }
            } else {
                this@favoritesGifViewObserverExtension.finish()
            }

            Log.d(this@favoritesGifViewObserverExtension.javaClass.simpleName, "GifsFavoriteListData Observe")
        })

    favoritesGifViewModel.setupGifsFavoritesData()

    return favoritesGifViewModel
}