/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 6:52 AM
 * Last modified 3/3/20 6:52 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.SharingProcess

import android.content.Intent
import android.net.Uri
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentActivity
import kotlinx.android.synthetic.main.gif_view.*
import kotlinx.android.synthetic.main.gif_view.view.*
import net.geeksempire.geeky.gify.R

class ControlGifShare (var fragmentActivity: FragmentActivity) : SharingInterface {

    fun initializeGifShare(gifLinkToShare: String, additionalText: String?) {

        val shareView = fragmentActivity.fullShareView
        shareView.startAnimation(AnimationUtils.loadAnimation(fragmentActivity, R.anim.slide_from_right))
        shareView.visibility = View.VISIBLE

        shareView.shareToPhone.setOnClickListener {

            startShareToPhoneProcess(
                gifLinkToShare,
                additionalText
            )
        }

        shareView.shareToWatch.setOnClickListener {

            startShareToOtherApplications(
                gifLinkToShare,
                additionalText
            )
        }
    }

    override fun sharingProcessCallback(gifLinkToShare: String, additionalText: String?) {

        startShareToOtherApplications(
            gifLinkToShare,
            additionalText
        )
    }

    private fun startShareToPhoneProcess(gifLinkToShare: String, additionalText: String?) {

        val remoteIntent = Intent(Intent.ACTION_VIEW)
            .addCategory(Intent.CATEGORY_BROWSABLE)
            .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            .setData(
                Uri.parse("https://www.geeksempire.xyz/Projects/Android/GeekyGify/ControlGeekyGifyShare.html?${Intent.EXTRA_STREAM}=${gifLinkToShare}" +
                        "&" +
                        "${Intent.EXTRA_TEXT}=${additionalText}"))

        fragmentActivity.startActivity(remoteIntent)
    }

    private fun startShareToOtherApplications (gifLinkToShare: String, additionalText: String?) {


    }
}