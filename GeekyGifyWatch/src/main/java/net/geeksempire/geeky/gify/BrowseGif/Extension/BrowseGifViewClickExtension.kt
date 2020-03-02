/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/2/20 4:50 AM
 * Last modified 3/2/20 3:36 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Extension

import android.view.View
import kotlinx.android.synthetic.main.browse_gif_list_view.*
import net.geeksempire.geeky.gify.BrowseGif.Data.EnqueueEndPointQuery
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.GiphyExplore.GiphyExplore
import net.geeksempire.geeky.gify.GiphyExplore.GiphySearchParameter
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.JsonRequestResponse

fun BrowseGifView.createClickListeners(queryType: String, categoryName: String, browseGifViewModel: BrowseGifViewModel) {

    exploreGifs.setOnClickListener {

        if (!gifViewer.isVisible || !fragmentPlaceHolder.isShown) {

            fragmentPlaceHolder.visibility = View.VISIBLE
            GiphyExplore()
                .invokeGiphyExplore(this@createClickListeners)
        }
    }

    nextGifPage.setOnClickListener {
        progressBarGifs.show()
        previousGifPage.visibility = View.VISIBLE

        BrowseGifViewModel.gifRequestOffset = BrowseGifViewModel.gifRequestOffset.plus(BrowseGifViewModel.gifRequestLimit)

        GiphySearchParameter(queryType, categoryName).also {

            EnqueueEndPointQuery()
                .giphyJsonObjectRequest(applicationContext,
                    it,
                    JsonRequestResponse().jsonRequestResponseHandler(applicationContext, browseGifViewModel))
        }
    }

    previousGifPage.setOnClickListener {
        progressBarGifs.show()

        BrowseGifViewModel.gifRequestOffset = BrowseGifViewModel.gifRequestOffset.minus(BrowseGifViewModel.gifRequestLimit)
        if (BrowseGifViewModel.gifRequestOffset < 0) {
            BrowseGifViewModel.gifRequestOffset = 0

            previousGifPage.visibility = View.GONE
        }

        GiphySearchParameter(queryType, categoryName).also {

            EnqueueEndPointQuery()
                .giphyJsonObjectRequest(applicationContext,
                    it,
                    JsonRequestResponse().jsonRequestResponseHandler(applicationContext, browseGifViewModel))
        }
    }
}