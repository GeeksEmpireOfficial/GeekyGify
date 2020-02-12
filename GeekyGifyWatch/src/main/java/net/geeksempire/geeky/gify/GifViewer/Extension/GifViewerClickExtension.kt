/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/11/20 4:41 PM
 * Last modified 2/11/20 3:02 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifViewer.Extension

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import androidx.wear.widget.ConfirmationOverlay
import com.google.android.wearable.intent.RemoteIntent
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

            if (gifUserIsVerified!!) {
                userVerifiedBadgeView.startAnimation(animationSlideOut)
                userVerifiedBadgeView.visibility = View.GONE
            }

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

            if (gifUserIsVerified!!) {
                userVerifiedBadgeView.startAnimation(animationSlideIn)
                userVerifiedBadgeView.visibility = View.VISIBLE
            }

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
                    gifLinkToDownload,
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

        Intent(Intent.ACTION_SEND).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)

            putExtra(Intent.EXTRA_STREAM, Uri.parse(linkToGif))
            putExtra(Intent.EXTRA_TEXT, context?.getString(R.string.app_name))

            this.type = "image/*"

            startActivity(this)
        }

        val resultReceiver = object : ResultReceiver(Handler()) {
            override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                if (resultCode == RemoteIntent.RESULT_OK) {

                    val confirmationOverlay = ConfirmationOverlay()
                        .setMessage(context?.getString(R.string.gifReadyOnPhone))
                        .setDuration(1500 * 1)
                        .setType(ConfirmationOverlay.SUCCESS_ANIMATION)
                    confirmationOverlay.showOn(activity)

                } else if (resultCode == RemoteIntent.RESULT_FAILED) {

                    val confirmationOverlay = ConfirmationOverlay()
                        .setMessage(context?.getString(R.string.errorOccurred))
                        .setDuration(1500 * 1)
                        .setType(ConfirmationOverlay.FAILURE_ANIMATION)
                    confirmationOverlay.showOn(activity)

                }
            }
        }

        val remoteIntent = Intent(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .setData(Uri.parse(/*linkToGif*/"https://www.geekygify.xyz/controlgeekygifshare.html?${Intent.EXTRA_STREAM}=${gifLinkToDownload}" +
                    "&" +
                    "${Intent.EXTRA_TEXT}=${context?.getString(R.string.app_name)}"))

        RemoteIntent.startRemoteActivity(
            context,
            remoteIntent,
            resultReceiver)
    }
}