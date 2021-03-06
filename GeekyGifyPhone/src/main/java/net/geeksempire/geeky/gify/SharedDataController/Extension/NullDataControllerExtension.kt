/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 6/19/20 3:26 PM
 * Last modified 6/19/20 3:16 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.SharedDataController.Extension

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.net.Uri
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.transition.ChangeBounds
import androidx.transition.ChangeImageTransform
import androidx.transition.TransitionManager
import androidx.transition.TransitionSet
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.load.resource.gif.GifDrawable
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import net.geeksempire.geeky.gify.GiphyExplore.GiphyExplore
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.SharedDataController.NullDataController
import net.geeksempire.geeky.gify.Utils.Converter.ConvertFile
import net.geeksempire.geeky.gify.Utils.UI.GlowAnimation
import net.geeksempire.geeky.gify.Utils.UI.PopupAppShortcuts
import net.geeksempire.geeky.gify.Utils.UI.SnackbarInteraction
import net.geeksempire.geeky.gify.Utils.UI.SnackbarView


fun NullDataController.setupClickNullDataControllerAdsApp() {

    val popupAppShortcuts: PopupAppShortcuts = PopupAppShortcuts(context!!)

    //Geeks Empire - Facebook Page
    Glide.with(context!!)
        .load("https://lh3.googleusercontent.com/_mOI9EcM700XeBDnSKVvVz1WTLVFxhnQzKXhYZ8y7hQDqALmQNnMtSJwTJLxMocge5A=w512-h512-n")
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(RequestOptions.circleCropTransform())
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                e?.printStackTrace()

                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                resource?.let {
                    popupAppShortcuts.create(
                        context!!.getString(R.string.geeksEmpireName),
                        context!!.getString(R.string.geeksEmpireName),
                        Icon.createWithBitmap(ConvertFile().drawableToBitmap(resource)),
                        context!!.getString(R.string.geeksEmpireLinkFacebook)
                    )
                }

                return true
            }
        })
        .submit()

    //T. Rex Runner
    Glide.with(context!!)
        .load("https://lh3.googleusercontent.com/f5fZLzzYj-Dx2emwCcPBkyqBrpXge2UAQhgDAzXzONszuME0sAyqq4nGeRZCn61uZg=s512")
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(RequestOptions.circleCropTransform())
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                e?.printStackTrace()

                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                resource?.let {
                    popupAppShortcuts.create(
                        context!!.getString(R.string.tRexRunnerName),
                        context!!.getString(R.string.tRexRunnerName),
                        Icon.createWithBitmap(ConvertFile().drawableToBitmap(resource)),
                        context!!.getString(R.string.tRexRunnerLink)
                    )
                }

                return true
            }
        })
        .submit()

    //Float It
    Glide.with(context!!)
        .load("https://lh3.googleusercontent.com/yS28x2BrP45C6mfVPP7rt4Zs84X18kBEZfNF3BsbY4IyUT8VAVtoPT-nNDD5B8fRsnuF=s512")
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(RequestOptions.circleCropTransform())
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                e?.printStackTrace()

                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                resource?.let {
                    popupAppShortcuts.create(
                        context!!.getString(R.string.floatItName),
                        context!!.getString(R.string.floatItName),
                        Icon.createWithBitmap(ConvertFile().drawableToBitmap(resource)),
                        context!!.getString(R.string.floatItLink)
                    )

                    activity?.runOnUiThread {
                        nullDataControllerBinding.floatItIcon.setImageDrawable(resource)
                    }
                }

                return true
            }
        })
        .submit()

    //Super Shortcuts
    Glide.with(context!!)
        .load("https://lh3.googleusercontent.com/AmmKC1aIYMA3rGjbtl0t3yketF3P_WN4JoPOylj0HFxs63QQ-7gvxg96A8ZPrs4oBKs=s512")
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .apply(RequestOptions.circleCropTransform())
        .addListener(object : RequestListener<Drawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                e?.printStackTrace()

                return false
            }

            override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                resource?.let {
                    popupAppShortcuts.create(
                        context!!.getString(R.string.superShortcutsName),
                        context!!.getString(R.string.superShortcutsName),
                        Icon.createWithBitmap(ConvertFile().drawableToBitmap(resource)),
                        context!!.getString(R.string.superShortcutsLink)
                    )

                    activity?.runOnUiThread {
                        nullDataControllerBinding.superShortcutsIcon.setImageDrawable(resource)
                    }
                }

                return true
            }
        })
        .submit()

    nullDataControllerBinding.floatItIcon.setOnClickListener {
        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.floatItLink))).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
    nullDataControllerBinding.floatItName.setOnClickListener {
        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.floatItLink))).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

    nullDataControllerBinding.superShortcutsIcon.setOnClickListener {
        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.superShortcutsLink))).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
    nullDataControllerBinding.superShortcutsName.setOnClickListener {
        Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.superShortcutsLink))).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }
}

fun NullDataController.setupNullDataControllerUI() {

    Glide.with(context!!)
        .asGif()
        .diskCacheStrategy(DiskCacheStrategy.DATA)
        .load("https://media0.giphy.com/media/ZCemAxolHlLetaTqLh/giphy.gif")
        .listener(object : RequestListener<GifDrawable> {
            override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<GifDrawable>?, isFirstResource: Boolean): Boolean {

                return false
            }

            override fun onResourceReady(resource: GifDrawable?, model: Any?, target: Target<GifDrawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                collectionGif.initial()

                trendingGif.initial()

                return false
            }

        })
        .into(nullDataControllerBinding.waitingView)

    SnackbarView()
        .snackBarViewSuccess((activity as AppCompatActivity?)!!, nullDataControllerBinding.mainViewNullDataController, getString(R.string.explore), getString(R.string.nullData),
            object : SnackbarInteraction {
                override fun onActionClick() {

                    GiphyExplore()
                        .invokeGiphyExplore(this@setupNullDataControllerUI)
                }
            })

    val animatable = context!!.getDrawable(R.drawable.animated_geeky_gify_text) as Animatable
    animatable.start()

    Glide.with(context!!)
        .load(animatable as Drawable)
        .into(nullDataControllerBinding.appNameView)

    val glowAnimation = GlowAnimation(context!!)

    with(nullDataControllerBinding.facebookIcon) {
        glowAnimation.valueAnimatorLoopTint(
            this,
            startValueColor = context!!.getColor(R.color.light), endValueColor = Color.TRANSPARENT,
            startDuration = 1777, endDuration = 777
        )
    }

    with(nullDataControllerBinding.rateReviewIcon) {
        glowAnimation.valueAnimatorLoopTint(
            this,
            startValueColor = context!!.getColor(R.color.light), endValueColor = Color.TRANSPARENT,
            startDuration = 1777, endDuration = 777
        )
    }

    with(nullDataControllerBinding.floatItName) {
        glowAnimation.shadowValueAnimatorLoop(
            view = this,
            startValueShadow = 0, endValueShadow = 29,
            startDuration = 1333, endDuration = 777,
            shadowColor = context!!.getColor(R.color.lighter),
            shadowX = 0f,
            shadowY = 0f
        )
    }

    with(nullDataControllerBinding.superShortcutsName) {
        glowAnimation.shadowValueAnimatorLoop(
            view = this,
            startValueShadow = 0, endValueShadow = 29,
            startDuration = 1333, endDuration = 777,
            shadowColor = context!!.getColor(R.color.lighter),
            shadowX = 0f,
            shadowY = 0f
        )
    }

}

fun NullDataController.changeWaitingView() {

    TransitionManager.beginDelayedTransition(
        nullDataControllerBinding.mainViewNullDataController, TransitionSet()
            .addTransition(ChangeBounds())
            .addTransition(ChangeImageTransform())
    )

    val viewLayoutParameter = nullDataControllerBinding.waitingView.layoutParams

    viewLayoutParameter.width = 10
    viewLayoutParameter.height = 10

    nullDataControllerBinding.waitingView.layoutParams = viewLayoutParameter
    nullDataControllerBinding.waitingView.visibility = View.GONE
}