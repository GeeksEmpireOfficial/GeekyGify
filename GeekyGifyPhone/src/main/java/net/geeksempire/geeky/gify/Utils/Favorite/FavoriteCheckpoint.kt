/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 6:52 AM
 * Last modified 3/3/20 6:40 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Favorite

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.Utils.LinkInformation.ExtractLinkData
import net.geeksempire.geeky.gify.Utils.Networking.DownloadGif
import java.io.File

class FavoriteCheckpoint(var context: Context) {

    fun gifFavorited(linkToGif: String) : Boolean{
        val geekyGifyCollectionFolderPath = context.externalMediaDirs[0].path + File.separator + "SharedGifCollection"
        File(geekyGifyCollectionFolderPath).let {
            if (!it.exists()) {
                it.mkdirs()
            }
        }

        val gifId = ExtractLinkData().extractGifId(linkToGif)
        val filePath = geekyGifyCollectionFolderPath + File.separator + "GeekyGify${gifId}" + ".GIF"
        val gifFile = File(filePath)

        return gifFile.exists()
    }

    fun favoriteIt(linkToGif: String) = CoroutineScope(Dispatchers.IO).launch {
         DownloadGif(context).downloadGifFile(linkToGif).await()
    }

    fun unFavoriteIt(linkToGif: String) {
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
            gifFile.delete()
        }
    }
}