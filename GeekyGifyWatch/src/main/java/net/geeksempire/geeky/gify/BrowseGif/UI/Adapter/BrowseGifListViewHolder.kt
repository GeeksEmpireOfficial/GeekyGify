/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 3:48 PM
 * Last modified 2/7/20 2:33 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.UI.Adapter

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.browse_gif_item_view.view.*

class BrowseGifListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mainView: ConstraintLayout = view.mainViewItem
    val gifPreview: ImageView = view.gifItemView
}