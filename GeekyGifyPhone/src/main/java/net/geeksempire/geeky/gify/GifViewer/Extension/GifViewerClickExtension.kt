/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 4:54 AM
 * Last modified 3/3/20 4:54 AM
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
import kotlinx.android.synthetic.main.gif_view.*
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

            /*User Profile*/
            userAvatarView.startAnimation(animationSlideOut)
            userAvatarView.visibility = View.GONE

            userNameView.startAnimation(animationSlideOut)
            userNameView.visibility = View.GONE

            userVerifiedBadgeView.startAnimation(animationSlideOut)
            userVerifiedBadgeView.visibility = View.GONE
            /*User Profile*/

            gifViewLayoutParameter.width = ViewGroup.LayoutParams.MATCH_PARENT
            gifViewLayoutParameter.height = ViewGroup.LayoutParams.MATCH_PARENT

        } else {

            val animationSlideIn = AnimationUtils.loadAnimation(context, R.anim.slide_from_right)

            shareGif.startAnimation(animationSlideIn)
            shareGif.visibility = View.VISIBLE

            makeFavorite.startAnimation(animationSlideIn)
            makeFavorite.visibility = View.VISIBLE

            /*User Profile*/
            userAvatarView.startAnimation(animationSlideIn)
            userAvatarView.visibility = View.VISIBLE

            userNameView.startAnimation(animationSlideIn)
            userNameView.visibility = View.VISIBLE

            userVerifiedBadgeView.startAnimation(animationSlideIn)
            userVerifiedBadgeView.visibility = View.VISIBLE
            /*User Profile*/

            gifViewLayoutParameter.width = calculateThirtyPercent(gifView.width)
            gifViewLayoutParameter.height = calculateThirtyPercent(gifView.height)

        }
        gifView.layoutParams = gifViewLayoutParameter

        viewExpanded = !viewExpanded

        false
    }

//    FavoriteCheckpoint(context!!)
//        .checkIfFavorite(makeFavorite, gifLinkToDownload)
//    makeFavorite.setOnLikeListener(object : OnLikeListener {
//        override fun liked(likeButton: LikeButton?) {
//            CoroutineScope(Dispatchers.IO).launch {
//
//                FavoriteIt(context!!)
//                    .addFavoriteGifDatabase(
//                        gifLinkToDownload, gifPreviewLink,
//                        gifUserName, gifUserAvatarUrl, gifUserIsVerified).await()
//
//                delay(555)
//                if (FavoriteCheckpoint(context!!).favoriteDatabaseCount() == 1) {
//                    Log.d("FavoriteDatabaseModified", "First Item Added")
//
//                    BrowseCategoryViewModel.favoriteFirstLastModified.postValue(true)
//                }
//            }
//        }
//
//        override fun unLiked(likeButton: LikeButton?) {
//            CoroutineScope(Dispatchers.IO).launch {
//
//                UnfavoriteIt(context!!)
//                    .removeFavoriteGifDatabase(
//                        gifLinkToDownload).await()
//
//                delay(555)
//                if (FavoriteCheckpoint(context!!).favoriteDatabaseCount() == 0) {
//                    Log.d("FavoriteDatabaseModified", "Last Item Removed")
//
//                    BrowseCategoryViewModel.favoriteFirstLastModified.postValue(true)
//                }
//            }
//        }
//    })
//
//    shareGif.setOnClickListener {
//
//        ControlGifShare(activity!!)
//            .initializeGifShare(gifLinkToDownload,
//                context?.getString(R.string.app_name)
//            )
//
//    }
}