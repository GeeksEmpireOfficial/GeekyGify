/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 9/17/20 7:23 AM
 * Last modified 9/17/20 7:15 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.UI.Adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.geeksempire.geeky.gify.BrowseGif.UI.Adapter.Data.BrowseGifItemData
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGif.Utils.RecyclerViewGifBrowseItemPress
import net.geeksempire.geeky.gify.R

class BrowseGifAdapter(var context: BrowseGifView,
                       var browseGifItemData: ArrayList<BrowseGifItemData>,
                       var recyclerViewGifBrowseItemPress: RecyclerViewGifBrowseItemPress
) : RecyclerView.Adapter<BrowseGifListViewHolder>() {

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

            recyclerViewGifBrowseItemPress.itemPressed(
                browseGifItemData[position].gifUserProfile,
                browseGifItemData[position].gifOriginalUri,
                browseGifItemData[position].linkToGif,
                browseGifItemData[position].gifPreviewUrl
            )

        }

        viewHoldBrowseGifList.gifPreview.setOnLongClickListener {

            browseGifItemData[position].gifUserProfile?.let {

                var creatorInformation = it.userName

                if (it.isUserVerified) {
                    creatorInformation = "@$creatorInformation ✔"
                }

                Toast.makeText(context, creatorInformation, Toast.LENGTH_LONG).show()

            }

            false
        }
    }
}