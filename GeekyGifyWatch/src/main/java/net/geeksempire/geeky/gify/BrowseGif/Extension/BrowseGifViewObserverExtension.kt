/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/2/20 4:50 AM
 * Last modified 3/2/20 3:37 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Extension

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.browse_gif_list_view.*
import kotlinx.android.synthetic.main.offline_indicator.view.*
import net.geeksempire.geeky.gify.BrowseGif.Data.EnqueueEndPointQuery
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.BrowseGifAdapter
import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.Data.GifUserProfile
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGif.Utils.RecyclerViewGifBrowseItemPress
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.GiphyExplore.GiphySearchParameter
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.JsonRequestResponse

fun BrowseGifView.createViewModelObserver (queryType: String, categoryName: String) : BrowseGifViewModel {

    gifList.layoutManager = GridLayoutManager(applicationContext, 2, RecyclerView.VERTICAL, false)

    val browseGifViewModel = ViewModelProvider(this@createViewModelObserver).get(BrowseGifViewModel::class.java)

    val recyclerViewGifBrowseItemPressHandler: RecyclerViewGifBrowseItemPress = object :
        RecyclerViewGifBrowseItemPress {
        override fun itemPressed(gifUserProfile: GifUserProfile?,
                                 gifOriginalUri: String, linkToGif: String, gifPreviewUri: String) {

            fragmentPlaceHolder.visibility = View.VISIBLE

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

    browseGifViewModel.gifsListData.observe(this@createViewModelObserver,
        Observer {
            if (it.size > 0) {
                gifList.visibility = View.VISIBLE
                progressBarGifs.hide()

                val browseGifAdapter = BrowseGifAdapter(this@createViewModelObserver, it, recyclerViewGifBrowseItemPressHandler)

                gifList.adapter = browseGifAdapter
                browseGifAdapter.notifyDataSetChanged()


                nextGifPage.visibility = if (queryType == BrowseGifViewModel.QUERY_TYPE.QUERY_SEARCH) {
                    View.VISIBLE
                } else {
                    View.GONE
                }
            }

            Log.d(this@createViewModelObserver.javaClass.simpleName, "GifsListData Observe")
        })

    browseGifViewModel.gifsListError.observe(this@createViewModelObserver,
        Observer { errorMessage ->

            if (errorMessage.isNotEmpty()) {
                val offlineIndicator = LayoutInflater.from(this@createViewModelObserver).inflate(R.layout.offline_indicator, mainView, false)

                mainView.addView(offlineIndicator)

                Glide.with(applicationContext)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .load(R.drawable.no_internet_connection)
                    .into(offlineIndicator.offlineWait)

                offlineIndicator.offlineWait.setOnClickListener {
                    startActivity(
                        Intent(Settings.ACTION_SETTINGS).addFlags(
                            Intent.FLAG_ACTIVITY_NEW_TASK))

                    this@createViewModelObserver.finish()
                }
            }
        })

    GiphySearchParameter(queryType, categoryName).also {

        EnqueueEndPointQuery()
            .giphyJsonObjectRequest(applicationContext,
                it,
                JsonRequestResponse().jsonRequestResponseHandler(applicationContext, browseGifViewModel))
    }

    return browseGifViewModel
}