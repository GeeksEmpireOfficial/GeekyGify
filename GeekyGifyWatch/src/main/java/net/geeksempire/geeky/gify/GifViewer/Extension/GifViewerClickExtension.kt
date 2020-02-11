/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 5:18 PM
 * Last modified 2/10/20 5:16 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifViewer.Extension

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.room.Room
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.like.LikeButton
import com.like.OnLikeListener
import kotlinx.android.synthetic.main.gif_view.*
import kotlinx.coroutines.*
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseNames
import net.geeksempire.geeky.gify.RoomDatabase.GifFavorite.FavoriteDataInterface
import net.geeksempire.geeky.gify.RoomDatabase.GifFavorite.FavoriteDataModel
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

    checkIfFavorite(context!!, makeFavorite, gifLinkToDownload)
    makeFavorite.setOnLikeListener(object : OnLikeListener {
        override fun liked(likeButton: LikeButton?) {
            CoroutineScope(Dispatchers.IO).launch {

                addFavoriteGifDatabase(context!!,
                    gifLinkToDownload,
                    gifUserName, gifUserAvatarUrl, gifUserIsVerified).await()
            }
        }

        override fun unLiked(likeButton: LikeButton?) {
            CoroutineScope(Dispatchers.IO).launch {

                removeFavoriteGifDatabase(context!!,
                    gifLinkToDownload).await()
            }
        }
    })
}

fun checkIfFavorite(context: Context, likeButton: LikeButton, gifUrl: String) = CoroutineScope(Dispatchers.IO).launch {

    if (context.getDatabasePath(DatabaseNames.GIF_FAVORITE_DATABASE_NAME).exists()) {

        val gifFavoriteDataInterface = Room.databaseBuilder(context, FavoriteDataInterface::class.java, DatabaseNames.GIF_FAVORITE_DATABASE_NAME)
            .build()

        val gifFavorited = gifFavoriteDataInterface
            .initDataAccessObject()
            .getFavoriteGif(gifUrl)?.GifFavorited ?: false

        withContext(Dispatchers.Main) {

            likeButton.isLiked = gifFavorited
        }

        gifFavoriteDataInterface.close()
    }
}

fun addFavoriteGifDatabase(context: Context,
                           gifUrl: String,
                           gifUsername: String?, gifUserAvatar: String?, gifUserVerified: Boolean?) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

    val gifFavoriteDataInterface = Room.databaseBuilder(context, FavoriteDataInterface::class.java, DatabaseNames.GIF_FAVORITE_DATABASE_NAME)
        .build()

    val favoriteDataModel = FavoriteDataModel(System.currentTimeMillis(), gifUrl,
        gifUsername.toString(), gifUserAvatar.toString(), gifUserVerified?:false,
        true)

    gifFavoriteDataInterface.initDataAccessObject().insertNewFavoriteData(favoriteDataModel)

    gifFavoriteDataInterface.close()
}

fun removeFavoriteGifDatabase(context: Context, gifUrl: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

    val gifFavoriteDataInterface = Room.databaseBuilder(context, FavoriteDataInterface::class.java, DatabaseNames.GIF_FAVORITE_DATABASE_NAME)
        .build()

    gifFavoriteDataInterface.initDataAccessObject().getFavoriteGif(gifUrl)?.let {
        gifFavoriteDataInterface.initDataAccessObject().delete(it)
    }

    gifFavoriteDataInterface.close()
}
