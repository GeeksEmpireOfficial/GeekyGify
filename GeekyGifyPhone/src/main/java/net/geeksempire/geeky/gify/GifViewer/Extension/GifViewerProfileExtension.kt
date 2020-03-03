/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 5:11 AM
 * Last modified 3/3/20 4:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifViewer.Extension

import android.content.res.ColorStateList
import android.graphics.Color
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.gif_view.*
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.R

fun GifViewer.setupUserProfileInformation() {

    if (!gifUserName.isNullOrBlank() && !gifUserAvatarUrl.isNullOrBlank()
        && context != null) {

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
                userVerifiedBadgeView.setImageDrawable(context?.getDrawable(R.drawable.icon_verified_badge))
            } else {
                userVerifiedBadgeView.setImageDrawable(context?.getDrawable(R.drawable.icon_verified_badge))
            }
        } else {
            userVerifiedBadgeView.setImageDrawable(context?.getDrawable(R.drawable.icon_verified_badge))
            userVerifiedBadgeView.imageTintList = ColorStateList.valueOf(Color.GRAY)
        }
    }
}