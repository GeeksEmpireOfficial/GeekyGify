/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/2/20 12:21 AM
 * Last modified 3/2/20 12:19 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.ViewHolders

import android.view.View
import androidx.appcompat.widget.AppCompatTextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.airbnb.lottie.LottieAnimationView
import kotlinx.android.synthetic.main.browse_gif_category_trending_item_view.view.*

class BrowseTrendingListViewHolder(view: View) : RecyclerView.ViewHolder(view) {

    val mainView: ConstraintLayout = view.mainViewItem
    val trendingBackground: LottieAnimationView = view.trendingBackground
    val trendingTitle: AppCompatTextView = view.trendingTitle
}