/*
 * Copyright © 2020 By Geeks Empire. 
 *
 * Created by Elias Fazel on 2/12/20 1:13 PM
 * Last modified 2/12/20 1:10 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.offline_indicator.view.*
import kotlinx.android.synthetic.main.share_controller.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.Networking.DownloadGif
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.NetworkConnectionListener
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.SystemCheckpoint


class ShareDataController : AppCompatActivity() {

    lateinit var networkConnectionListener: NetworkConnectionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.share_controller)

        val vmBuilder = VmPolicy.Builder()
        StrictMode.setVmPolicy(vmBuilder.build())

        val systemCheckpoint = SystemCheckpoint(applicationContext)

        networkConnectionListener = NetworkConnectionListener(
            this@ShareDataController,
            mainView,
            systemCheckpoint)

        intent.data?.let { intentData ->

            if (systemCheckpoint.networkConnection()) {

                val waitingGifs = arrayOf(
                    R.drawable.waiting_cube,
                    R.drawable.waiting_earth,
                    R.drawable.waiting_robot)

                Glide.with(applicationContext)
                    .asGif()
                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                    .load(waitingGifs.random())
                    .into(waitingView)

                val linkToGif = intentData.getQueryParameter(Intent.EXTRA_STREAM).toString()
                val additionalText = intentData.getQueryParameter(Intent.EXTRA_TEXT).toString()

                Log.d(this.javaClass.simpleName, linkToGif)
                Log.d(this.javaClass.simpleName, additionalText)

                CoroutineScope(Dispatchers.Default).launch {
                    Intent(Intent.ACTION_SEND).apply {

                        val gifFile = DownloadGif(applicationContext).downloadGifFile(linkToGif).await()

                        if (gifFile.exists()) {

                            this.type = "image/*"

                            this.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(gifFile))
                            this.putExtra(Intent.EXTRA_TEXT, additionalText)

                            this.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                            this.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)

                            startActivity(Intent.createChooser(this, additionalText))
                        } else {
                            val snackbarError = Snackbar.make(mainView, getString(R.string.downloadErrorOccurred), Snackbar.LENGTH_LONG)
                            snackbarError.show()
                            snackbarError.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                                override fun onShown(transientBottomBar: Snackbar?) {
                                    super.onShown(transientBottomBar)
                                }

                                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                                    super.onDismissed(transientBottomBar, event)

                                    Handler().postDelayed({
                                        this@ShareDataController.finish()
                                    }, 500)
                                }
                            })
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

        if (intent.data == null) {
            val snackbarError = Snackbar.make(mainView, getString(R.string.errorOccurred), Snackbar.LENGTH_LONG)
            snackbarError.show()
            snackbarError.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                override fun onShown(transientBottomBar: Snackbar?) {
                    super.onShown(transientBottomBar)
                }

                override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                    super.onDismissed(transientBottomBar, event)

                    Handler().postDelayed({
                        this@ShareDataController.finish()
                    }, 500)
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        this@ShareDataController.finish()
        networkConnectionListener.unRegisterDefaultNetworkCallback()
    }
}
