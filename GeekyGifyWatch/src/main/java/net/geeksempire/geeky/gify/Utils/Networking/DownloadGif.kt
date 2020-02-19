/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/18/20 7:14 PM
 * Last modified 2/18/20 6:57 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Networking

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DownloadGif (var context: Context) {

    @Throws(Exception::class)
    fun downloadGifFile(linkToGif: String) : Deferred<File> = CoroutineScope(SupervisorJob() + Dispatchers.Default).async {
        Log.d(this@DownloadGif.javaClass.simpleName, linkToGif)

        val downloadGifByte = URL(linkToGif).readBytes()

        val filePath = context.externalMediaDirs[0].path + "/GeekyGify" + ".GIF"
        val gifFile = File(filePath)
        val fileOutputStream = FileOutputStream(gifFile)

        fileOutputStream.write(downloadGifByte)

        fileOutputStream.flush()
        fileOutputStream.close()

        gifFile
    }
}