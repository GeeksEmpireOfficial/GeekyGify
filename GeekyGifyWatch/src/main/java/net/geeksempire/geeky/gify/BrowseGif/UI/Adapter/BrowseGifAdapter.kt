/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 2:34 PM
 * Last modified 2/10/20 2:34 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.UI.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.Data.BrowseGifItemData
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.UI.RecyclerViewGifBrowseItemPress

class BrowseGifAdapter(var browseGifView: BrowseGifView,
                       var browseGifItemData: ArrayList<BrowseGifItemData>,
                       var recyclerViewGifBrowseItemPress: RecyclerViewGifBrowseItemPress) : RecyclerView.Adapter<BrowseGifListViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BrowseGifListViewHolder {

        return BrowseGifListViewHolder(LayoutInflater.from(browseGifView).inflate(R.layout.browse_gif_item_view, parent, false))
    }

    override fun getItemCount(): Int {

        return browseGifItemData.size
    }

    override fun onBindViewHolder(viewHoldBrowseGifList: BrowseGifListViewHolder, position: Int) {

        viewHoldBrowseGifList.mainView.setBackgroundColor(Color.parseColor(browseGifItemData[position].backgroundColor))

        Glide.with(browseGifView)
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(browseGifItemData[position].gifPreviewUrl)
            .into(viewHoldBrowseGifList.gifPreview)

        viewHoldBrowseGifList.gifPreview.setOnClickListener {

            recyclerViewGifBrowseItemPress.itemPressed(
                browseGifItemData[position].gifUserProfile,
                browseGifItemData[position].gifOriginalUri
            )

        }

        viewHoldBrowseGifList.gifPreview.setOnLongClickListener {

            false
        }
    }
}