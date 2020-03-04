/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 10:10 AM
 * Last modified 3/4/20 10:08 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.TrendingSectionUI

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.geeksempire.geeky.gify.BrowseGif.Utils.RecyclerViewGifBrowseItemPress
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.ViewModel.BrowseTrendingGifItemData

class TrendingGifAdapter(var trendingGif: TrendingGif,
                         var recyclerViewGifBrowseItemPress: RecyclerViewGifBrowseItemPress) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val trendingGifAdapterData = ArrayList<BrowseTrendingGifItemData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BrowseTrendingGifListViewHolder(LayoutInflater.from(trendingGif.nullDataController.context).inflate(R.layout.browse_trend_gif_item_view, parent, false))
    }

    override fun getItemCount(): Int {

        return trendingGifAdapterData.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        when (viewHolder) {
            is BrowseTrendingGifListViewHolder -> {
                viewHolder.mainView.setBackgroundColor(Color.parseColor(trendingGifAdapterData[position].backgroundColor))

                Glide.with(trendingGif.nullDataController.context!!)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .load(trendingGifAdapterData[position].gifPreviewUrl)
                    .into(viewHolder.gifPreview)

                viewHolder.gifPreview.setOnClickListener {

                    recyclerViewGifBrowseItemPress.itemPressed(
                        trendingGifAdapterData[position].gifUserProfile,
                        trendingGifAdapterData[position].gifOriginalUri,
                        trendingGifAdapterData[position].linkToGif,
                        trendingGifAdapterData[position].gifPreviewUrl
                    )

                }

                viewHolder.gifPreview.setOnLongClickListener {

                    false
                }
            }
        }
    }
}