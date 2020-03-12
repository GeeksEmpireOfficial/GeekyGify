/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/11/20 5:52 PM
 * Last modified 3/11/20 5:23 PM
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
            .into(gifViewBinding.userAvatarView)

        Glide.with(context!!)
            .asDrawable()
            .load(gifUserAvatarUrl)
            .diskCacheStrategy(DiskCacheStrategy.DATA)
            .apply(RequestOptions.circleCropTransform())
            .into(gifViewBinding.userAvatarView)

        gifViewBinding.userNameView.text = "@${gifUserName}"

        if (gifUserIsVerified != null) {
            if (!gifUserIsVerified!!) {
                gifViewBinding.userVerifiedBadgeView.imageTintList = ColorStateList.valueOf(Color.GRAY)
                gifViewBinding.userVerifiedBadgeView.setImageDrawable(context?.getDrawable(R.drawable.icon_verified_badge))
            } else {
                gifViewBinding.userVerifiedBadgeView.setImageDrawable(context?.getDrawable(R.drawable.icon_verified_badge))
            }
        } else {
            gifViewBinding.userVerifiedBadgeView.setImageDrawable(context?.getDrawable(R.drawable.icon_verified_badge))
            gifViewBinding.userVerifiedBadgeView.imageTintList = ColorStateList.valueOf(Color.GRAY)
        }
    }
}