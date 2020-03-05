/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/5/20 8:49 AM
 * Last modified 3/5/20 8:28 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.CollectionSectionUI.Utils

import androidx.recyclerview.widget.DiffUtil
import net.geeksempire.geeky.gify.ViewModel.BrowseCollectionGifItemData

class CollectionDiffUtil (
    private val oldCollectionData: ArrayList<BrowseCollectionGifItemData>,
    private val newCollectionData: ArrayList<BrowseCollectionGifItemData>) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {

        return (oldCollectionData[oldItemPosition].gifId == newCollectionData[newItemPosition].gifId)
    }

    override fun getOldListSize(): Int {

        return oldCollectionData.size
    }

    override fun getNewListSize(): Int {

        return newCollectionData.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCollectionGifItemData = oldCollectionData[oldItemPosition]
        val newCollectionGifItemData = newCollectionData[newItemPosition]

        return (oldCollectionGifItemData == newCollectionGifItemData)
    }

    override fun getChangePayload(oldItemPosition: Int, newItemPosition: Int): Any? {

        return super.getChangePayload(oldItemPosition, newItemPosition)
    }
}