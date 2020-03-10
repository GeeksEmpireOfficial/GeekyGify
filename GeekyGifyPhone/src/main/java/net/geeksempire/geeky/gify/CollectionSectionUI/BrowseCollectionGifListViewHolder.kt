/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/10/20 2:40 PM
 * Last modified 3/10/20 2:24 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.CollectionSectionUI

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.browse_collection_gif_item_view.view.*

class BrowseCollectionGifListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mainView: ConstraintLayout = view.mainViewCollectionItem
    val gifPreview: ImageView = view.gifItemView
}