/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 1:39 PM
 * Last modified 2/13/20 1:14 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.UI.Adapter

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.favorites_gif_item_view.view.*

class FavoritesGifListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val mainView: ConstraintLayout = view.mainViewItem
    val gifPreview: ImageView = view.gifItemView
}