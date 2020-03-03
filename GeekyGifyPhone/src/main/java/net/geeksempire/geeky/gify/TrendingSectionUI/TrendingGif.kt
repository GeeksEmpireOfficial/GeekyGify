/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 4:54 AM
 * Last modified 3/3/20 4:53 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.TrendingSectionUI

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.shared_data_controller.*
import kotlinx.android.synthetic.main.trending_gif.*
import kotlinx.android.synthetic.main.trending_gif.mainView
import net.geeksempire.geeky.gify.BrowseGif.Data.EnqueueEndPointQuery
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.BrowseGif.Utils.RecyclerViewGifBrowseItemPress
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.ServerConnection.DownloadData.EndPointAddress
import net.geeksempire.geeky.gify.ServerConnection.DownloadData.GiphySearchParameter
import net.geeksempire.geeky.gify.ServerConnection.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.ServerConnection.ViewModel.GifUserProfile
import net.geeksempire.geeky.gify.SharedDataController.NullDataController
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.JsonRequestResponseInterface
import net.geeksempire.geeky.gify.Utils.UI.SnackbarInteraction
import net.geeksempire.geeky.gify.Utils.UI.SnackbarView
import org.json.JSONObject

class TrendingGif (var nullDataController: NullDataController) {

    fun initial() {
        nullDataController.trendingList.layoutManager = GridLayoutManager(nullDataController.context!!, 2, RecyclerView.VERTICAL, false)

        val browseGifViewModel =
            ViewModelProvider(nullDataController).get(BrowseGifViewModel::class.java)

        GiphySearchParameter(
            categoryName = null,
            queryType = EndPointAddress.QUERY_TYPE.QUERY_TREND
        ).also {
            EnqueueEndPointQuery().giphyJsonObjectRequest(nullDataController.context!!, it,
                object : JsonRequestResponseInterface {
                    override fun jsonRequestResponseSuccessHandler(
                        rawDataJsonObject: JSONObject,
                        colorsList: ArrayList<String>) {

                        browseGifViewModel.setupGifsBrowserData(rawDataJsonObject, colorsList)
                    }

                    override fun jsonRequestResponseFailureHandler(jsonError: String?) {

                    }
                })
        }

        val recyclerViewGifBrowseItemPressHandler: RecyclerViewGifBrowseItemPress = object : RecyclerViewGifBrowseItemPress {
            override fun itemPressed(gifUserProfile: GifUserProfile?,
                                     gifOriginalUri: String, linkToGif: String, gifPreviewUri: String) {

                nullDataController.activity!!.fragmentPlaceHolderGifViewer.visibility = View.VISIBLE

                nullDataController.gifViewer.arguments = Bundle().apply {
                    putString(GiphyJsonDataStructure.DATA_URL, linkToGif)
                    putString(GiphyJsonDataStructure.DATA_IMAGES_PREVIEW_GIF, gifPreviewUri)
                    putString(GiphyJsonDataStructure.DATA_IMAGES_ORIGINAL, gifOriginalUri)

                    gifUserProfile?.let { gifUserProfile ->
                        putString(GiphyJsonDataStructure.DATA_USER_NAME, gifUserProfile.userName)
                        putString(GiphyJsonDataStructure.DATA_USER_AVATAR_URL, gifUserProfile.userAvatarUrl)
                        putBoolean(GiphyJsonDataStructure.DATA_USER_IS_VERIFIED, gifUserProfile.isUserVerified)
                    }
                }

                (nullDataController.activity as AppCompatActivity).supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, 0)
                    .replace(R.id.fragmentPlaceHolderGifViewer, nullDataController.gifViewer, "GIF VIEWER")
                    .commit()

            }
        }

        val trendingGifAdapter: TrendingGifAdapter = TrendingGifAdapter(this@TrendingGif, recyclerViewGifBrowseItemPressHandler)

        browseGifViewModel.gifsListData.observe(nullDataController,
            Observer {

                trendingGifAdapter.trendingGifAdapterData.clear()
                trendingGifAdapter.trendingGifAdapterData.addAll(it)

                nullDataController.trendingList.adapter = trendingGifAdapter

            })

        browseGifViewModel.gifsListError.observe(nullDataController,
            Observer {
                SnackbarView().snackBarViewFail(nullDataController.context!!,
                    nullDataController.mainView,
                    nullDataController.context!!.getString(R.string.downloadErrorOccurred), object : SnackbarInteraction {})
            })
    }
}