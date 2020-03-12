/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/11/20 5:52 PM
 * Last modified 3/11/20 5:31 PM
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
import kotlinx.coroutines.*
import net.geeksempire.geeky.gify.BrowseGif.Data.EnqueueEndPointQuery
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.BrowseGif.Utils.RecyclerViewGifBrowseItemPress
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.ServerConnection.DownloadData.EndPoint.EndPointAddress
import net.geeksempire.geeky.gify.ServerConnection.DownloadData.EndPoint.GiphySearchParameter
import net.geeksempire.geeky.gify.SharedDataController.DataController
import net.geeksempire.geeky.gify.SharedDataController.Extension.changeWaitingView
import net.geeksempire.geeky.gify.SharedDataController.NullDataController
import net.geeksempire.geeky.gify.Utils.Calculations.DpToPixel
import net.geeksempire.geeky.gify.Utils.Calculations.rowCount
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.JsonRequestResponseInterface
import net.geeksempire.geeky.gify.Utils.UI.SnackbarInteraction
import net.geeksempire.geeky.gify.Utils.UI.SnackbarView
import net.geeksempire.geeky.gify.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.ViewModel.GifUserProfile
import org.json.JSONObject

class TrendingGif(var nullDataController: NullDataController) {

    val context = nullDataController.requireContext()

    fun initial() = CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
        delay(1357)

        nullDataController.nullDataControllerBinding.trendingGifInclude.trendingList.layoutManager = GridLayoutManager(
            context,
            rowCount(nullDataController.nullDataControllerBinding.trendingGifInclude.root.height, DpToPixel(87f, context).toInt()),
            RecyclerView.HORIZONTAL, false
        )

        val browseGifViewModel =
            ViewModelProvider(nullDataController).get(BrowseGifViewModel::class.java)

        GiphySearchParameter(
            categoryName = null,
            queryType = EndPointAddress.QUERY_TYPE.QUERY_TREND
        ).also {
            EnqueueEndPointQuery().giphyJsonObjectRequest(context, it,
                object : JsonRequestResponseInterface {
                    override fun jsonRequestResponseSuccessHandler(
                        rawDataJsonObject: JSONObject,
                        colorsList: ArrayList<String>
                    ) {

                        browseGifViewModel.setupTrendingGifsBrowserData(rawDataJsonObject, colorsList)
                    }

                    override fun jsonRequestResponseFailureHandler(jsonError: String?) {

                    }
                })
        }

        val recyclerViewGifBrowseItemPressHandler: RecyclerViewGifBrowseItemPress =
            object : RecyclerViewGifBrowseItemPress {

                override fun itemPressedTrending(
                    gifUserProfile: GifUserProfile?,
                    gifOriginalUri: String, linkToGif: String, gifPreviewUri: String
                ) {

                    (nullDataController.requireActivity() as DataController).dataControllerBinding.fragmentPlaceHolderGifViewer.visibility = View.VISIBLE

                    nullDataController.gifViewer.arguments = Bundle().apply {
                        putString(GiphyJsonDataStructure.DATA_URL, linkToGif)
                        putString(GiphyJsonDataStructure.DATA_IMAGES_PREVIEW_GIF, gifPreviewUri)
                        putString(GiphyJsonDataStructure.DATA_IMAGES_ORIGINAL, gifOriginalUri)

                        gifUserProfile?.let { gifUserProfile ->
                            putString(
                                GiphyJsonDataStructure.DATA_USER_NAME,
                                gifUserProfile.userName
                            )
                            putString(
                                GiphyJsonDataStructure.DATA_USER_AVATAR_URL,
                                gifUserProfile.userAvatarUrl
                            )
                            putBoolean(
                                GiphyJsonDataStructure.DATA_USER_IS_VERIFIED,
                                gifUserProfile.isUserVerified
                            )
                        }
                    }

                    (nullDataController.activity as AppCompatActivity).supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_right, 0)
                        .replace(
                            R.id.fragmentPlaceHolderGifViewer,
                            nullDataController.gifViewer,
                            "GIF VIEWER"
                        )
                        .commit()

                }
            }

        val trendingGifAdapter: TrendingGifAdapter = TrendingGifAdapter(this@TrendingGif, recyclerViewGifBrowseItemPressHandler)

        browseGifViewModel.gifsListDataTrending.observe(nullDataController,
            Observer {
                nullDataController.changeWaitingView()

                trendingGifAdapter.trendingGifAdapterData.clear()
                trendingGifAdapter.trendingGifAdapterData.addAll(it)

                nullDataController.nullDataControllerBinding.trendingGifInclude.trendingList.adapter = trendingGifAdapter
                (nullDataController.nullDataControllerBinding.trendingGifInclude.trendingList.adapter as TrendingGifAdapter).notifyDataSetChanged()

            })

        browseGifViewModel.gifsListError.observe(nullDataController,
            Observer {
                SnackbarView().snackBarViewFail(context,
                    nullDataController.nullDataControllerBinding.mainViewNullDataController,
                    context.getString(R.string.downloadErrorOccurred),
                    object : SnackbarInteraction {})
            })
    }
}