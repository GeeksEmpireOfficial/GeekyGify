/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 3:48 PM
 * Last modified 2/7/20 2:33 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import androidx.wear.widget.WearableLinearLayoutManager

class BrowseCategoryWearLayoutManager(var maxIconProgress: Float = 0.65f) : WearableLinearLayoutManager.LayoutCallback() {

    private var progressToCenter: Float = 0f

    override fun onLayoutFinished(child: View, parent: RecyclerView) {
        child.apply {
            // Figure out % progress from top to bottom
            val centerOffset = height.toFloat() / 2.0f / parent.height.toFloat()
            val yRelativeToCenterOffset = y / parent.height + centerOffset

            // Normalize for center
            progressToCenter = Math.abs(0.5f - yRelativeToCenterOffset)
            // Adjust to the maximum scale
            progressToCenter = Math.min(progressToCenter, maxIconProgress)

            scaleX = 1 - progressToCenter
            scaleY = 1 - progressToCenter
        }
    }
}