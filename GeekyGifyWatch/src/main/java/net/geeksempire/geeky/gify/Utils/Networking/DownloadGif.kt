/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/20/20 2:24 PM
 * Last modified 2/20/20 12:52 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Networking

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import net.geeksempire.geeky.gify.Utils.LinkInformation.ExtractLinkData
import java.io.File
import java.io.FileOutputStream
import java.net.URL

class DownloadGif (var context: Context) {

    @Throws(Exception::class)
    fun downloadGifFile(linkToGif: String) : Deferred<File> = CoroutineScope(SupervisorJob() + Dispatchers.Default).async {
        Log.d(this@DownloadGif.javaClass.simpleName, linkToGif)

        val geekyGifyCollectionFolderPath = context.externalMediaDirs[0].path + File.separator + "SharedGifCollection"
        File(geekyGifyCollectionFolderPath).let {
            if (!it.exists()) {
                it.mkdirs()
            }
        }

        val gifId = ExtractLinkData().extractGifId(linkToGif)
        val filePath = geekyGifyCollectionFolderPath + File.separator + "GeekyGify${gifId}" + ".GIF"
        val gifFile = File(filePath)

        if (gifFile.exists()) {

        } else {
            val downloadGifByte = URL(linkToGif).readBytes()

            val fileOutputStream = FileOutputStream(gifFile)

            fileOutputStream.write(downloadGifByte)

            fileOutputStream.flush()
            fileOutputStream.close()
        }

        gifFile
    }
}