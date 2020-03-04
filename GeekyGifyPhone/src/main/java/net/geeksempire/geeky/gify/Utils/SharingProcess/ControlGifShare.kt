/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 6:58 AM
 * Last modified 3/4/20 6:13 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.SharingProcess

import android.content.Intent
import android.net.Uri
import android.util.Log
import android.view.View
import android.view.animation.AnimationUtils
import androidx.fragment.app.FragmentActivity
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import kotlinx.android.synthetic.main.gif_view.*
import kotlinx.android.synthetic.main.gif_view.view.*
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.UI.SnackbarInteraction
import net.geeksempire.geeky.gify.Utils.UI.SnackbarView

class ControlGifShare (var fragmentActivity: FragmentActivity) : SharingInterface {

    object DATA_TYPE_WATCH {
        const val SHARED_GIF_PATH = "/shared_gif_link"

        const val SHARED_GIF_KEY_LINK = "gif_link"
        const val SHARED_GIF_KEY_PREVIEW = "gif_preview"
        const val SHARED_GIF_KEY_USERNAME = "gif_username"
        const val SHARED_GIF_KEY_AVATAR = "gif_avatar"
        const val SHARED_GIF_KEY_VERIFIED = "gif_verified"
    }

    fun initializeGifShare(gifLinkToShare: String,
                           additionalText: String?,
                           gifPreviewLink: String,
                           gifUsername: String?,
                           gifUserAvatar: String?,
                           gifUserVerified: Boolean?) {

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

            startShareToWatchProcess(
                gifLinkToShare,
                gifPreviewLink,
                gifUsername,
                gifUserAvatar,
                gifUserVerified
            )
        }
    }

    override fun sharingProcessCallback(gifLinkToShare: String, additionalText: String?) {

        startShareToPhoneProcess(
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

    private fun startShareToWatchProcess(gifLinkToShare: String,
                                         gifPreviewLink: String,
                                         gifUsername: String?,
                                         gifUserAvatar: String?,
                                         gifUserVerified: Boolean?) {

        val putDataMapRequest = PutDataMapRequest.create(DATA_TYPE_WATCH.SHARED_GIF_PATH)
        putDataMapRequest.dataMap.putString(DATA_TYPE_WATCH.SHARED_GIF_KEY_LINK, gifLinkToShare)
        putDataMapRequest.dataMap.putString(DATA_TYPE_WATCH.SHARED_GIF_KEY_PREVIEW, gifPreviewLink)
        putDataMapRequest.dataMap.putString(DATA_TYPE_WATCH.SHARED_GIF_KEY_USERNAME, gifUsername)
        putDataMapRequest.dataMap.putString(DATA_TYPE_WATCH.SHARED_GIF_KEY_AVATAR, gifUserAvatar)
        putDataMapRequest.dataMap.putBoolean(DATA_TYPE_WATCH.SHARED_GIF_KEY_VERIFIED, gifUserVerified?:false)

        val putDataRequest = putDataMapRequest.asPutDataRequest()
        putDataRequest.setUrgent()

        val dataItemTask = Wearable.getDataClient(fragmentActivity).putDataItem(putDataRequest)
        dataItemTask
            .addOnSuccessListener { dataItem ->
                Log.d(this@ControlGifShare.javaClass.simpleName, "Data Sent Successfully")

                SnackbarView()
                    .snackBarViewSuccess(fragmentActivity, fragmentActivity.mainViewItem,  fragmentActivity.getString(R.string.dataSendToWatchDone),
                        object : SnackbarInteraction {
                            override fun onActionClick() {

                            }
                        })
            }
            .addOnFailureListener { exception ->
                Log.d(this@ControlGifShare.javaClass.simpleName, "$exception")

                SnackbarView()
                    .snackBarViewFail(fragmentActivity, fragmentActivity.mainViewItem,  fragmentActivity.getString(R.string.dataSendToWatchError),
                        object : SnackbarInteraction {
                            override fun onSnackbarRemoved() {

                            }
                        })
            }
    }
}