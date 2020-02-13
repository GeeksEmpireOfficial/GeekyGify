/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 12:51 PM
 * Last modified 2/13/20 12:44 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Extension

import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.share_controller.*
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.ShareDataController

fun ShareDataController.setupLoadingAnimation() {

    val waitingGifs = arrayOf(
        R.drawable.waiting_cube,
        R.drawable.waiting_earth,
        R.drawable.waiting_robot)

    Glide.with(applicationContext)
        .asGif()
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .load(waitingGifs.random())
        .into(waitingView)
}