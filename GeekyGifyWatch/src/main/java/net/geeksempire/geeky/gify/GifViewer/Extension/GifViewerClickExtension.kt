/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 3:50 PM
 * Last modified 2/13/20 3:42 PM
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
import kotlinx.android.synthetic.main.gif_view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.GifFavorite.Util.FavoriteCheckpoint
import net.geeksempire.geeky.gify.GifFavorite.Util.FavoriteIt
import net.geeksempire.geeky.gify.GifFavorite.Util.UnfavoriteIt
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.Calculations.calculateThirtyPercent
import net.geeksempire.geeky.gify.Utils.SharingProcess.ControlGifShare

fun GifViewer.setupGifViewClickListener() {

    closeFragment.setOnClickListener {
        activity?.let {
            it.supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(0, R.anim.slide_to_right)
                .remove(this@setupGifViewClickListener)
                .commit()
        }
    }

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

        if (viewExpanded)  {

            val animationSlideOut = AnimationUtils.loadAnimation(context, R.anim.slide_to_right)

            shareGif.startAnimation(animationSlideOut)
            shareGif.visibility = View.GONE

            makeFavorite.startAnimation(animationSlideOut)
            makeFavorite.visibility = View.GONE

            userAvatarView.startAnimation(animationSlideOut)
            userAvatarView.visibility = View.GONE

            userNameView.startAnimation(animationSlideOut)
            userNameView.visibility = View.GONE

            userVerifiedBadgeView.startAnimation(animationSlideOut)
            userVerifiedBadgeView.visibility = View.GONE

            gifViewLayoutParameter.width = ViewGroup.LayoutParams.MATCH_PARENT
            gifViewLayoutParameter.height = ViewGroup.LayoutParams.MATCH_PARENT

        } else {

            val animationSlideIn = AnimationUtils.loadAnimation(context, R.anim.slide_from_right)

            shareGif.startAnimation(animationSlideIn)
            shareGif.visibility = View.VISIBLE

            makeFavorite.startAnimation(animationSlideIn)
            makeFavorite.visibility = View.VISIBLE

            userAvatarView.startAnimation(animationSlideIn)
            userAvatarView.visibility = View.VISIBLE

            userNameView.startAnimation(animationSlideIn)
            userNameView.visibility = View.VISIBLE

            userVerifiedBadgeView.startAnimation(animationSlideIn)
            userVerifiedBadgeView.visibility = View.VISIBLE

            gifViewLayoutParameter.width = calculateThirtyPercent(gifView.width)
            gifViewLayoutParameter.height = calculateThirtyPercent(gifView.height)

        }
        gifView.layoutParams = gifViewLayoutParameter

        viewExpanded = !viewExpanded

        false
    }

    FavoriteCheckpoint(context!!)
        .checkIfFavorite(makeFavorite, gifLinkToDownload)
    makeFavorite.setOnLikeListener(object : OnLikeListener {
        override fun liked(likeButton: LikeButton?) {
            CoroutineScope(Dispatchers.IO).launch {

                FavoriteIt(context!!).addFavoriteGifDatabase(
                    gifLinkToDownload, gifPreviewLink,
                    gifUserName, gifUserAvatarUrl, gifUserIsVerified).await()
            }
        }

        override fun unLiked(likeButton: LikeButton?) {
            CoroutineScope(Dispatchers.IO).launch {

                UnfavoriteIt(context!!).removeFavoriteGifDatabase(gifLinkToDownload).await()
            }
        }
    })

    shareGif.setOnClickListener {

        ControlGifShare(activity!!)
            .initializeGifShare(gifLinkToDownload,
                context?.getString(R.string.app_name)
            )

    }
}