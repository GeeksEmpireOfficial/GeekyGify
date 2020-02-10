/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/9/20 4:09 PM
 * Last modified 2/9/20 4:07 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifViewer.Extension

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.gif_view.*
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.Calculations.calculateThirtyPercent

fun GifViewer.setupUserProfileInformation() {

    if (gifUserName != null && gifUserAvatarUrl != null) {

        Glide.with(context!!)
            .asGif()
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .load(gifUserAvatarUrl)
            .apply(RequestOptions.circleCropTransform())
            .into(userAvatarView)

        userNameView.text = "@${gifUserName}"

        if (gifUserIsVerified != null) {
            if (!gifUserIsVerified!!) {
                userVerifiedBadgeView.imageTintList = ColorStateList.valueOf(Color.GRAY)
            } else {

            }
        }
    }
}