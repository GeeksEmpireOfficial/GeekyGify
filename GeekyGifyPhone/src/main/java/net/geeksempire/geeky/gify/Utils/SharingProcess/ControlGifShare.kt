/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/11/20 5:52 PM
 * Last modified 3/11/20 5:52 PM
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
import com.google.android.gms.wearable.PutDataMapRequest
import com.google.android.gms.wearable.Wearable
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.UI.SnackbarInteraction
import net.geeksempire.geeky.gify.Utils.UI.SnackbarView

class ControlGifShare (private var gifViewer: GifViewer) : SharingInterface {

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

        val shareView = gifViewer.gifViewBinding.fullShareView
        shareView.startAnimation(AnimationUtils.loadAnimation(gifViewer.requireContext(), R.anim.slide_from_right))
        shareView.visibility = View.VISIBLE

        gifViewer.gifViewBinding.shareToPhone.setOnClickListener {

            startShareToPhoneProcess(
                gifLinkToShare,
                additionalText
            )
        }

        gifViewer.gifViewBinding.shareToWatch.setOnClickListener {

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

        gifViewer.startActivity(remoteIntent)
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

        val dataItemTask = Wearable.getDataClient(gifViewer.requireActivity()).putDataItem(putDataRequest)
        dataItemTask
            .addOnSuccessListener { dataItem ->
                Log.d(this@ControlGifShare.javaClass.simpleName, "Data Sent Successfully")

                SnackbarView()
                    .snackBarViewSuccess(gifViewer.requireContext(), gifViewer.gifViewBinding.mainViewGifViewer,  gifViewer.getString(android.R.string.ok), gifViewer.getString(R.string.dataSendToWatchDone),
                        object : SnackbarInteraction {
                            override fun onActionClick() {

                            }
                        })
            }
            .addOnFailureListener { exception ->
                Log.d(this@ControlGifShare.javaClass.simpleName, "$exception")

                SnackbarView()
                    .snackBarViewFail(gifViewer.requireContext(), gifViewer.gifViewBinding.mainViewGifViewer,  gifViewer.getString(R.string.dataSendToWatchError),
                        object : SnackbarInteraction {
                            override fun onSnackbarRemoved() {

                            }
                        })
            }
    }
}