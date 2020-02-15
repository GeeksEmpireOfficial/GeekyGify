/*
 * Copyright © 2020 By Geeks Empire. 
 *
 * Created by Elias Fazel on 2/14/20 4:26 PM
 * Last modified 2/14/20 4:17 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.offline_indicator.view.*
import kotlinx.android.synthetic.main.share_controller.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.Networking.DownloadGif
import net.geeksempire.geeky.gify.Utils.Extension.setupLoadingAnimation
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.NetworkConnectionListener
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.SystemCheckpoint
import net.geeksempire.geeky.gify.Utils.uI.SnackbarView


class ShareDataController : AppCompatActivity() {

    private lateinit var networkConnectionListener: NetworkConnectionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_controller)

        val vmBuilder = VmPolicy.Builder()
        StrictMode.setVmPolicy(vmBuilder.build())

        val systemCheckpoint = SystemCheckpoint(applicationContext)

        val snackbarView = SnackbarView()

        networkConnectionListener = NetworkConnectionListener(
            this@ShareDataController,
            mainView,
            systemCheckpoint)


        if (intent.data == null) {

            snackbarView.snackBarViewFail(this@ShareDataController,
                mainView,
                getString(R.string.errorOccurred))

        } else {

            intent.data?.let { intentData ->

                if (systemCheckpoint.networkConnection()) {
                    setupLoadingAnimation()

                    val linkToGif = intentData.getQueryParameter(Intent.EXTRA_STREAM).toString()
                    val additionalText = intentData.getQueryParameter(Intent.EXTRA_TEXT).toString()

                    Log.d(this.javaClass.simpleName, linkToGif)
                    Log.d(this.javaClass.simpleName, additionalText)

                    CoroutineScope(Dispatchers.Default).launch {
                        val gifFile = DownloadGif(applicationContext).downloadGifFile(linkToGif).await()

                        Intent(Intent.ACTION_SEND).apply {

                            if (gifFile.exists()) {

                                this.type = "image/*"

                                this.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(gifFile))
                                this.putExtra(Intent.EXTRA_TEXT, additionalText)

                                this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                this.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                                startActivity(Intent.createChooser(this, additionalText))
                            } else {

                                snackbarView.snackBarViewFail(this@ShareDataController,
                                    mainView,
                                    getString(R.string.downloadErrorOccurred))

                            }
                        }
                    }

                } else {

                    window.statusBarColor = getColor(R.color.cyberGreen)
                    window.navigationBarColor = getColor(R.color.cyberGreen)

                    val offlineIndicator = LayoutInflater.from(applicationContext).inflate(R.layout.offline_indicator, mainView, false)

                    mainView.addView(offlineIndicator)

                    Glide.with(applicationContext)
                        .asGif()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .load(R.drawable.no_internet_connection)
                        .into(offlineIndicator.offlineWait)

                    offlineIndicator.offlineWait.setOnClickListener {
                        startActivity(Intent(Settings.ACTION_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                        this@ShareDataController.finish()
                    }
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        this@ShareDataController.finish()
        networkConnectionListener.unRegisterDefaultNetworkCallback()
    }
}
