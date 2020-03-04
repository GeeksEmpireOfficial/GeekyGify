/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 6:58 AM
 * Last modified 3/4/20 6:51 AM
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
            .load(gifUserAvatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .apply(RequestOptions.circleCropTransform())
            .into(userAvatarView)

        Glide.with(context!!)
            .asDrawable()
            .load(gifUserAvatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
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