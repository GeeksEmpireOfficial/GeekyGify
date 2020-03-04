/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 10:18 AM
 * Last modified 3/4/20 10:18 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.CollectionSectionUI

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.geeksempire.geeky.gify.BrowseGif.Utils.RecyclerViewGifBrowseItemPress
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.ViewModel.BrowseCollectionGifItemData

class CollectionGifAdapter(var collectionGif: CollectionGif,
                           private var recyclerViewGifBrowseItemPress: RecyclerViewGifBrowseItemPress) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val collectionGifAdapterData = ArrayList<BrowseCollectionGifItemData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BrowseCollectionGifListViewHolder(LayoutInflater.from(collectionGif.nullDataController.context).inflate(R.layout.browse_collection_gif_item_view, parent, false))
    }

    override fun getItemCount(): Int {

        return collectionGifAdapterData.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        when (viewHolder) {
            is BrowseCollectionGifListViewHolder -> {
                viewHolder.mainView.setBackgroundColor(Color.parseColor(collectionGifAdapterData[position].backgroundColor))

                Glide.with(collectionGif.nullDataController.context!!)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .load(collectionGifAdapterData[position].gifDrawable)
                    .into(viewHolder.gifPreview)

                viewHolder.gifPreview.setOnClickListener {


                }

                viewHolder.gifPreview.setOnLongClickListener {

                    false
                }
            }
        }
    }
}