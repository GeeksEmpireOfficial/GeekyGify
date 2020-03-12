/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/11/20 4:31 PM
 * Last modified 3/11/20 4:15 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.CollectionSectionUI

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import net.geeksempire.geeky.gify.BrowseGif.Utils.RecyclerViewGifBrowseItemPress
import net.geeksempire.geeky.gify.CollectionSectionUI.Utils.CollectionDiffUtil
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.ViewModel.BrowseCollectionGifItemData


class CollectionGifAdapter(var collectionGif: CollectionGif,
                           private var recyclerViewGifBrowseItemPress: RecyclerViewGifBrowseItemPress) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    val collectionGifAdapterData = ArrayList<BrowseCollectionGifItemData>()

    fun updateCollectionData(newCollectionGifItemData: ArrayList<BrowseCollectionGifItemData>) {

        val diffCallback = CollectionDiffUtil(collectionGifAdapterData, newCollectionGifItemData)

        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.collectionGifAdapterData.clear()
        this.collectionGifAdapterData.addAll(newCollectionGifItemData)

        diffResult.dispatchUpdatesTo(this@CollectionGifAdapter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {

        return BrowseCollectionGifListViewHolder(LayoutInflater.from(collectionGif.nullDataController.context).inflate(R.layout.browse_collection_gif_item_view, parent, false))
    }

    override fun getItemCount(): Int {

        return collectionGifAdapterData.size
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int, payloads: MutableList<Any>) {
        super.onBindViewHolder(viewHolder, position, payloads)

        when (viewHolder) {
            is BrowseCollectionGifListViewHolder -> {
                viewHolder.mainView.setBackgroundColor(Color.parseColor(collectionGifAdapterData[position].backgroundColor))

                Glide.with(collectionGif.nullDataController.requireContext())
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .load(collectionGifAdapterData[position].gifDrawable)
                    .into(viewHolder.gifPreview)
            }
        }
    }

    override fun onBindViewHolder(viewHolder: RecyclerView.ViewHolder, position: Int) {

        when (viewHolder) {
            is BrowseCollectionGifListViewHolder -> {
                viewHolder.mainView.setBackgroundColor(Color.parseColor(collectionGifAdapterData[position].backgroundColor))

                Glide.with(collectionGif.nullDataController.requireContext())
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .load(collectionGifAdapterData[position].gifDrawable)
                    .into(viewHolder.gifPreview)

                viewHolder.gifPreview.setOnClickListener {

                    recyclerViewGifBrowseItemPress.itemPressedCollection(
                        collectionGifAdapterData[position].gifDrawable,
                        collectionGifAdapterData[position].gifId
                    )
                }

                viewHolder.gifPreview.setOnLongClickListener {

                    false
                }
            }
        }
    }
}