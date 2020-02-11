/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/11/20 11:17 AM
 * Last modified 2/11/20 10:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Extension

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.browse_gif_list_view.*
import net.geeksempire.geeky.gify.BrowseGif.Data.EnqueueSearch
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.BrowseGifAdapter
import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.Data.GifUserProfile
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.GiphyExplore.GiphySearchParameter
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.ServerConnections.JsonRequestResponse
import net.geeksempire.geeky.gify.Utils.UI.RecyclerViewGifBrowseItemPress

fun BrowseGifView.createViewModelObserver (categoryName: String) : BrowseGifViewModel {

    gifList.layoutManager = GridLayoutManager(applicationContext, 2, RecyclerView.VERTICAL, false)

    val browseGifViewModel = ViewModelProvider(this@createViewModelObserver).get(BrowseGifViewModel::class.java)

    val recyclerViewGifBrowseItemPressHandler: RecyclerViewGifBrowseItemPress = object : RecyclerViewGifBrowseItemPress {
        override fun itemPressed(
            gifUserProfile: GifUserProfile?,
            gifOriginalUri: String,
            linkToGif: String
        ) {
            fragmentGifViewer.visibility = View.VISIBLE

            gifViewer.arguments = Bundle().apply {
                putString(GiphyJsonDataStructure.DATA_URL, linkToGif)
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
                .replace(R.id.fragmentGifViewer, gifViewer, "GIF VIEWER")
                .commit()

        }
    }

    browseGifViewModel.gifsListData.observe(this@createViewModelObserver,
        Observer {
            if (it.size > 0) {
                gifList.visibility = View.VISIBLE
                progressBarGifs.hide()

                val browseGifAdapter = BrowseGifAdapter(this@createViewModelObserver, it, recyclerViewGifBrowseItemPressHandler)

                gifList.adapter = browseGifAdapter
                browseGifAdapter.notifyDataSetChanged()

                nextGifPage.visibility = View.VISIBLE
            }

            Log.d(this@createViewModelObserver.javaClass.simpleName, "GifsListData Observe")
        })

    GiphySearchParameter(categoryName).also {

        EnqueueSearch()
            .giphyJsonObjectRequest(applicationContext,
                it,
                JsonRequestResponse().jsonRequestResponseHandler(applicationContext, browseGifViewModel))
    }

    return browseGifViewModel
}