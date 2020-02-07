/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 10:53 AM
 * Last modified 2/7/20 10:47 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Adapter

import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.geeksempire.geeky.gify.BrowseGif.Adapter.Data.BrowseGifItemData
import net.geeksempire.geeky.gify.R

class BrowseGifAdapter(var context: Context, var browseGifItemData: ArrayList<BrowseGifItemData>) : RecyclerView.Adapter<BrowseGifListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseGifListViewHolder {

        return BrowseGifListViewHolder(LayoutInflater.from(context).inflate(R.layout.browse_gif_item_view, parent, false))
    }

    override fun getItemCount(): Int {

        return browseGifItemData.size
    }

    override fun onBindViewHolder(viewHoldBrowseGifList: BrowseGifListViewHolder, position: Int) {

        viewHoldBrowseGifList.mainView.setBackgroundColor(Color.parseColor(browseGifItemData[position].backgroundColor))
        Glide.with(context)
              .asGif()
              .diskCacheStrategy(DiskCacheStrategy.DATA)
              .load(browseGifItemData[position].gifPreviewUrl)
              .into(viewHoldBrowseGifList.gifPreview)

        viewHoldBrowseGifList.gifPreview.setOnClickListener {
            //browseGifItemData[position].gifOriginalUri
        }
    }
}