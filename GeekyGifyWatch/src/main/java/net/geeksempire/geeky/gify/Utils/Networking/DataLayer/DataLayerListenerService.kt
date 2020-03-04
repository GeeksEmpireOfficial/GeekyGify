/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 6:58 AM
 * Last modified 3/4/20 6:58 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */
package net.geeksempire.geeky.gify.Utils.Networking.DataLayer

import android.content.Intent
import android.util.Log
import com.google.android.gms.wearable.DataEvent
import com.google.android.gms.wearable.DataEventBuffer
import com.google.android.gms.wearable.DataMapItem
import com.google.android.gms.wearable.WearableListenerService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BuildConfig
import net.geeksempire.geeky.gify.EntryConfiguration
import net.geeksempire.geeky.gify.GifFavorite.Util.FavoriteIt

class DataLayerListenerService : WearableListenerService() {

    companion object {
        var nodeId: String? = null
    }

    object DATA_TYPE_WATCH {
        const val SHARED_GIF_PATH = "/shared_gif_link"

        const val SHARED_GIF_KEY_LINK = "gif_link"
        const val SHARED_GIF_KEY_PREVIEW = "gif_preview"
        const val SHARED_GIF_KEY_USERNAME = "gif_username"
        const val SHARED_GIF_KEY_AVATAR = "gif_avatar"
        const val SHARED_GIF_KEY_VERIFIED = "gif_verified"
    }

    override fun onDataChanged(dataEventBuffer: DataEventBuffer) {

        for (dataEvent in dataEventBuffer) {
            val uri = dataEvent.dataItem.uri
            nodeId = uri.host

            if (dataEvent.type == DataEvent.TYPE_CHANGED) {
                when (dataEvent.dataItem.uri.path) {
                    DATA_TYPE_WATCH.SHARED_GIF_PATH -> {

                        val dataMapItem = DataMapItem.fromDataItem(dataEvent.dataItem)

                        val gifLinkToShare = dataMapItem.dataMap.getString(DATA_TYPE_WATCH.SHARED_GIF_KEY_LINK)
                        val gifPreviewLink = dataMapItem.dataMap.getString(DATA_TYPE_WATCH.SHARED_GIF_KEY_PREVIEW)
                        val gifUsername = dataMapItem.dataMap.getString(DATA_TYPE_WATCH.SHARED_GIF_KEY_USERNAME)
                        val gifUserAvatar = dataMapItem.dataMap.getString(DATA_TYPE_WATCH.SHARED_GIF_KEY_AVATAR)
                        val gifUserVerified = dataMapItem.dataMap.getBoolean(DATA_TYPE_WATCH.SHARED_GIF_KEY_VERIFIED)

                        extractSharedData(
                            gifLinkToShare,
                            gifPreviewLink,
                            gifUsername,
                            gifUserAvatar,
                            gifUserVerified
                        )

                        if (BuildConfig.DEBUG) { println("*** Shared Data ${dataMapItem.dataMap} ***") }
                    }
                }
            }
        }
    }

    private fun extractSharedData(gifLinkToShare: String,
                                  gifPreviewLink: String,
                                  gifUsername: String?,
                                  gifUserAvatar: String?,
                                  gifUserVerified: Boolean?) = CoroutineScope(Dispatchers.Default).launch {

        if (gifLinkToShare.isNotEmpty()) {
            Log.d(this@DataLayerListenerService.javaClass.simpleName, gifLinkToShare)

            FavoriteIt(applicationContext)
                .addFavoriteGifDatabase(gifLinkToShare, gifPreviewLink, gifUsername, gifUserAvatar, gifUserVerified).await()

            startActivity(Intent(applicationContext, EntryConfiguration::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

        }
    }
}