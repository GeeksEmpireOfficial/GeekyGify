/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 3:48 PM
 * Last modified 2/7/20 3:43 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Extension

import android.view.ViewGroup
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import kotlinx.android.synthetic.main.gif_view.*
import net.geeksempire.geeky.gify.BrowseGif.UI.GifViewer
import net.geeksempire.geeky.gify.Utils.Calculations.calculateThirtyPercent

fun GifViewer.setupGifViewClickListener() {
    gifView.setOnClickListener {

    }

    var viewExpanded: Boolean = false
    gifView.setOnLongClickListener {

        TransitionManager.beginDelayedTransition(
            mainViewItem, TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
        )

        val gifViewLayoutParameter: ViewGroup.LayoutParams = gifView.layoutParams
        gifViewLayoutParameter.width = if (viewExpanded) ViewGroup.LayoutParams.MATCH_PARENT else calculateThirtyPercent(gifView.width)
        gifViewLayoutParameter.height = if (viewExpanded) ViewGroup.LayoutParams.MATCH_PARENT else calculateThirtyPercent(gifView.height)
        gifView.layoutParams = gifViewLayoutParameter

        viewExpanded = !viewExpanded

        false
    }
}