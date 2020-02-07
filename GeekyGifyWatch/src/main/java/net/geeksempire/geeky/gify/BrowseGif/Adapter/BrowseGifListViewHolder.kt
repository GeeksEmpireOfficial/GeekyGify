/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 10:53 AM
 * Last modified 2/7/20 10:53 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Adapter

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.browse_gif_item_view.view.*

class BrowseGifListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mainView: ConstraintLayout = view.mainViewItem
    val gifPreview: ImageView = view.gifItemView
}