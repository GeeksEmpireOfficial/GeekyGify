/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/18/20 5:56 PM
 * Last modified 2/18/20 5:56 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.DataController.Extension

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.received_data_controller.*
import net.geeksempire.geeky.gify.DataController.ReceivedDataController
import net.geeksempire.geeky.gify.R

fun ReceivedDataController.setupLoadingAnimation() {

    val waitingGifs = arrayOf(
        R.drawable.waiting_cube,
        R.drawable.waiting_earth,
        R.drawable.waiting_robot)

    Glide.with(context!!)
        .asGif()
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .load(waitingGifs.random())
        .into(waitingView)
}