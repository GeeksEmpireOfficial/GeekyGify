/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/20/20 12:35 PM
 * Last modified 2/20/20 12:32 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Extension

import android.view.View
import kotlinx.android.synthetic.main.browse_gif_list_view.*
import net.geeksempire.geeky.gify.BrowseGif.Data.EnqueueSearch
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.GiphyExplore.GiphyExplore
import net.geeksempire.geeky.gify.GiphyExplore.GiphySearchParameter
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.JsonRequestResponse

fun BrowseGifView.createClickListeners(categoryName: String, browseGifViewModel: BrowseGifViewModel) {

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

        GiphySearchParameter(categoryName).also {

            EnqueueSearch()
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

        GiphySearchParameter(categoryName).also {

            EnqueueSearch()
                .giphyJsonObjectRequest(applicationContext,
                    it,
                    JsonRequestResponse().jsonRequestResponseHandler(applicationContext, browseGifViewModel))
        }
    }
}