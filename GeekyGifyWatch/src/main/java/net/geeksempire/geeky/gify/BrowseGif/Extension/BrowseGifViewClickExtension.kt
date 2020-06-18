/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 6/18/20 11:18 AM
 * Last modified 6/18/20 10:50 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Extension

import android.view.View
import net.geeksempire.geeky.gify.BrowseGif.Data.EnqueueEndPointQuery
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.GiphyExplore.GiphyExplore
import net.geeksempire.geeky.gify.GiphyExplore.GiphySearchParameter
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.JsonRequestResponse

fun BrowseGifView.createClickListeners(queryType: String, categoryName: String, browseGifViewModel: BrowseGifViewModel) {

    browseGifListViewBinding.exploreGifs.setOnClickListener {

        if (!gifViewer.isVisible || !browseGifListViewBinding.fragmentPlaceHolder.isShown) {

            browseGifListViewBinding.fragmentPlaceHolder.visibility = View.VISIBLE
            GiphyExplore()
                .invokeGiphyExplore(this@createClickListeners)
        }
    }

    browseGifListViewBinding.nextGifPage.setOnClickListener {
        browseGifListViewBinding.progressBarGifs.show()
        browseGifListViewBinding.previousGifPage.visibility = View.VISIBLE

        BrowseGifViewModel.gifRequestOffset = BrowseGifViewModel.gifRequestOffset.plus(BrowseGifViewModel.gifRequestLimit)

        GiphySearchParameter(queryType, categoryName).also {

            EnqueueEndPointQuery()
                .giphyJsonObjectRequest(applicationContext,
                    it,
                    JsonRequestResponse().jsonRequestResponseHandler(applicationContext, browseGifViewModel))
        }
    }

    browseGifListViewBinding.previousGifPage.setOnClickListener {
        browseGifListViewBinding.progressBarGifs.show()

        BrowseGifViewModel.gifRequestOffset = BrowseGifViewModel.gifRequestOffset.minus(BrowseGifViewModel.gifRequestLimit)
        if (BrowseGifViewModel.gifRequestOffset < 0) {
            BrowseGifViewModel.gifRequestOffset = 0

            browseGifListViewBinding.previousGifPage.visibility = View.GONE
        }

        GiphySearchParameter(queryType, categoryName).also {

            EnqueueEndPointQuery()
                .giphyJsonObjectRequest(applicationContext,
                    it,
                    JsonRequestResponse().jsonRequestResponseHandler(applicationContext, browseGifViewModel))
        }
    }
}