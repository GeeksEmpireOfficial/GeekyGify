/*
 * Copyright © 2020 By Geeks Empire. 
 *
 * Created by Elias Fazel on 2/11/20 4:41 PM
 * Last modified 2/11/20 4:39 PM
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
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.Network.DownloadGif


class ShareDataController : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val vmBuilder = VmPolicy.Builder()
        StrictMode.setVmPolicy(vmBuilder.build())

        intent.data?.let { intentData ->

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
                    }
                }
            }
        }
    }

    override fun onPause() {
        super.onPause()

        this@ShareDataController.finish()
    }
}
