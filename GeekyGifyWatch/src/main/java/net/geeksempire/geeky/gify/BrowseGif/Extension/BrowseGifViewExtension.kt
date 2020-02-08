/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 10:06 AM
 * Last modified 2/8/20 9:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Extension

import android.content.Context
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.browse_gif_list_view.*
import net.geeksempire.geeky.gify.BrowseGif.Data.EnqueueSearch
import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.BrowseGifAdapter
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.Utils.Giphy.GiphyExplore
import net.geeksempire.geeky.gify.Utils.Giphy.GiphySearchParameter
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources
import net.geeksempire.geeky.gify.Utils.ServerConnections.JsonRequestResponseInterface
import org.json.JSONObject

fun BrowseGifView.createViewModelObserver (categoryName: String) : BrowseGifViewModel {

    gifList.layoutManager = GridLayoutManager(applicationContext, 2, RecyclerView.VERTICAL, false)

    val browseGifViewModel = ViewModelProvider(this@createViewModelObserver).get(BrowseGifViewModel::class.java)

    browseGifViewModel.gifsListData.observe(this@createViewModelObserver,
        Observer {
            if (it.size > 0) {
                gifList.visibility = View.VISIBLE
                progressBarGifs.hide()

                val browseGifAdapter = BrowseGifAdapter(this@createViewModelObserver, it)

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
                jsonRequestResponse(applicationContext, browseGifViewModel))
    }

    createClickListeners(categoryName, browseGifViewModel)

    return browseGifViewModel
}

fun BrowseGifView.createClickListeners(categoryName: String, browseGifViewModel: BrowseGifViewModel) {

    exploreGifs.setOnClickListener {

        GiphyExplore()
            .invokeGiphyExplore(this@createClickListeners)
    }

    nextGifPage.setOnClickListener {
        progressBarGifs.show()
        previousGifPage.visibility = View.VISIBLE

        BrowseGifViewModel.gifRequestOffset = BrowseGifViewModel.gifRequestOffset.plus(BrowseGifViewModel.gifRequestLimit)

        GiphySearchParameter(categoryName).also {

            EnqueueSearch()
                .giphyJsonObjectRequest(applicationContext,
                    it,
                    jsonRequestResponse(applicationContext, browseGifViewModel))
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
                    jsonRequestResponse(applicationContext, browseGifViewModel))
        }
    }
}

fun jsonRequestResponse(context: Context, browseGifViewModel: BrowseGifViewModel): JsonRequestResponseInterface {
    return object : JsonRequestResponseInterface {

        override fun jsonRequestResponseHandler(rawDataJsonObject: JSONObject, colorsList: ArrayList<String>) {

            browseGifViewModel.setupGifsBrowserData(rawDataJsonObject,
                GetResources(context).getNeonColors())
        }
    }
}