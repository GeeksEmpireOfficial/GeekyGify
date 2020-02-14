/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 5:20 PM
 * Last modified 2/13/20 5:15 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders

import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.browse_gif_category_item_view.view.*

class BrowseCategoryListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val mainView: ConstraintLayout = view.mainViewItem
    val categoryIconLeft: TextView = view.categoryIconLeft
    val categoryIconRight: TextView = view.categoryIconRight

}