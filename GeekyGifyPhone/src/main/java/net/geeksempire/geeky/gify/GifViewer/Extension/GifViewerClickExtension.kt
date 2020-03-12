/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/11/20 5:14 PM
 * Last modified 3/11/20 5:06 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifViewer.Extension

import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.Calculations.calculateThirtyPercent
import net.geeksempire.geeky.gify.Utils.Favorite.FavoriteCheckpoint
import net.geeksempire.geeky.gify.Utils.SharingProcess.ControlGifShare

fun GifViewer.setupGifViewClickListener() {

    gifViewBinding.gifView.setOnClickListener {

    }

    var viewExpanded: Boolean = false
    gifViewBinding.gifView.setOnLongClickListener {

        TransitionManager.beginDelayedTransition(
            gifViewBinding.mainViewGifViewer, TransitionSet()
                .addTransition(ChangeBounds())
                .addTransition(ChangeImageTransform())
        )

        val gifViewLayoutParameter: ViewGroup.LayoutParams = gifViewBinding.gifView.layoutParams

        if (viewExpanded)  {

            val animationSlideOut = AnimationUtils.loadAnimation(context, R.anim.slide_to_right)

            gifViewBinding.shareGif.startAnimation(animationSlideOut)
            gifViewBinding.shareGif.visibility = View.GONE

            gifViewBinding.makeFavorite.startAnimation(animationSlideOut)
            gifViewBinding.makeFavorite.visibility = View.GONE

            /*User Profile*/
            gifViewBinding.userAvatarView.startAnimation(animationSlideOut)
            gifViewBinding.userAvatarView.visibility = View.GONE

            gifViewBinding.userNameView.startAnimation(animationSlideOut)
            gifViewBinding.userNameView.visibility = View.GONE

            gifViewBinding.userVerifiedBadgeView.startAnimation(animationSlideOut)
            gifViewBinding.userVerifiedBadgeView.visibility = View.GONE
            /*User Profile*/

            gifViewLayoutParameter.width = ViewGroup.LayoutParams.MATCH_PARENT
            gifViewLayoutParameter.height = ViewGroup.LayoutParams.MATCH_PARENT

        } else {

            val animationSlideIn = AnimationUtils.loadAnimation(context, R.anim.slide_from_right)

            gifViewBinding.shareGif.startAnimation(animationSlideIn)
            gifViewBinding.shareGif.visibility = View.VISIBLE

            gifViewBinding.makeFavorite.startAnimation(animationSlideIn)
            gifViewBinding.makeFavorite.visibility = View.VISIBLE

            /*User Profile*/
            gifViewBinding.userAvatarView.startAnimation(animationSlideIn)
            gifViewBinding.userAvatarView.visibility = View.VISIBLE

            gifViewBinding.userNameView.startAnimation(animationSlideIn)
            gifViewBinding.userNameView.visibility = View.VISIBLE

            gifViewBinding.userVerifiedBadgeView.startAnimation(animationSlideIn)
            gifViewBinding.userVerifiedBadgeView.visibility = View.VISIBLE
            /*User Profile*/

            gifViewLayoutParameter.width = calculateThirtyPercent(gifViewBinding.gifView.width)
            gifViewLayoutParameter.height = calculateThirtyPercent(gifViewBinding.gifView.height)

        }
        gifViewBinding.gifView.layoutParams = gifViewLayoutParameter

        viewExpanded = !viewExpanded

        false
    }

    val favoriteCheckpoint: FavoriteCheckpoint = FavoriteCheckpoint(context!!)
    if (favoriteCheckpoint.gifFavorited(gifLinkToDownload)) {
        gifViewBinding.makeFavorite.isLiked = true
    }
    gifViewBinding.makeFavorite.setOnLikeListener(object : OnLikeListener {
        override fun liked(likeButton: LikeButton?) {
            CoroutineScope(Dispatchers.IO).launch {

                favoriteCheckpoint.favoriteIt(gifLinkToDownload)
            }
        }

        override fun unLiked(likeButton: LikeButton?) {
            CoroutineScope(Dispatchers.IO).launch {

                favoriteCheckpoint.unFavoriteIt(gifLinkToDownload)
            }
        }
    })

    gifViewBinding.shareGif.setOnClickListener {

        ControlGifShare(activity!!)
            .initializeGifShare(
                gifLinkToDownload, context?.getString(R.string.app_name),
                gifPreviewLink,
                gifUserName,
                gifUserAvatarUrl,
                gifUserIsVerified
            )

    }
}