/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/2/20 7:23 AM
 * Last modified 3/2/20 7:22 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.DataController

import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.null_data_controller.*
import net.geeksempire.geeky.gify.DataController.Extension.setupClickNullDataControllerAdsApp
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.UI.GlowAnimation
import net.geeksempire.geeky.gify.Utils.UI.SnackbarView

class NullDataController : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.null_data_controller, container, false)

        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val glowAnimation = GlowAnimation(context!!)

        SnackbarView()
            .snackBarViewSuccess((activity as AppCompatActivity?)!!, mainView, getString(R.string.nullData))

        Handler().postDelayed({
            Glide.with(context!!)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .load("https://media0.giphy.com/media/ZCemAxolHlLetaTqLh/giphy.gif")
                .into(waitingView)
        }, 357)

        facebookIcon.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.facebookPageLink))).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
        }

        rateReviewIcon.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.playStoreLink) + context!!.packageName)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
        }

        val animatable = context!!.getDrawable(R.drawable.animated_geeky_gify_text) as Animatable
        animatable.start()

        Glide.with(context!!)
            .load(animatable as Drawable)
            .into(appNameView)

        setupClickNullDataControllerAdsApp()

        with(facebookIcon) {
            glowAnimation.valueAnimatorLoopTint(
                this,
                startValueColor = context!!.getColor(R.color.light), endValueColor = Color.TRANSPARENT,
                startDuration = 1777, endDuration = 777
            )
        }

        with(rateReviewIcon) {
            glowAnimation.valueAnimatorLoopTint(
                this,
                startValueColor = context!!.getColor(R.color.light), endValueColor = Color.TRANSPARENT,
                startDuration = 1777, endDuration = 777
            )
        }

        with(floatItName) {
            glowAnimation.shadowValueAnimatorLoop(
                view = this,
                startValueShadow = 0, endValueShadow = 29,
                startDuration = 1333, endDuration = 777,
                shadowColor = context!!.getColor(R.color.lighter),
                shadowX = 0f,
                shadowY = 0f
            )
        }

        with(superShortcutsName) {
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
}