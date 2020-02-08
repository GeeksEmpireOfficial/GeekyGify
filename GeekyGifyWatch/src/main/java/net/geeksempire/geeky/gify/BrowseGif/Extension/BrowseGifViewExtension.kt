/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 4:35 PM
 * Last modified 2/7/20 4:21 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Extension

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

fun BrowseGifView.createViewModelObserver (categoryName: String) : BrowseGifViewModel {

    gifList.layoutManager = GridLayoutManager(applicationContext, 3, RecyclerView.VERTICAL, false)

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
        EnqueueSearch().giphyJsonObjectRequest(applicationContext, it, browseGifViewModel)
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

        GiphySearchParameter(categoryName, requestOffset = BrowseGifViewModel.gifRequestOffset).also {
            EnqueueSearch().giphyJsonObjectRequest(applicationContext, it, browseGifViewModel)
        }
    }

    previousGifPage.setOnClickListener {
        progressBarGifs.show()

        BrowseGifViewModel.gifRequestOffset = BrowseGifViewModel.gifRequestOffset.minus(BrowseGifViewModel.gifRequestLimit)
        if (BrowseGifViewModel.gifRequestOffset < 0) {
            BrowseGifViewModel.gifRequestOffset = 0

            previousGifPage.visibility = View.GONE
        }

        GiphySearchParameter(categoryName, requestOffset = BrowseGifViewModel.gifRequestOffset).also {
            EnqueueSearch().giphyJsonObjectRequest(applicationContext, it, browseGifViewModel)
        }
    }
}