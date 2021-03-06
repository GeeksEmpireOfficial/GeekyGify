/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/18/20 6:56 PM
 * Last modified 2/18/20 6:19 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders

import android.view.View
import android.widget.ImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.browse_gif_social_media_item_view.view.*

class BrowseSocialMediaListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val mainView: ConstraintLayout = view.mainViewItem
    val rateReview: ImageView = view.rateReviewIcon
    val facebookIcon: ImageView = view.facebookIcon

}