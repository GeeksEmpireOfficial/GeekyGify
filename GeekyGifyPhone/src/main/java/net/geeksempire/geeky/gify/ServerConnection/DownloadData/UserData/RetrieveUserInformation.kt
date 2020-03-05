/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/5/20 8:01 AM
 * Last modified 3/5/20 6:53 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.ServerConnection.DownloadData.UserData

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.async
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.ServerConnection.DownloadData.EndPoint.EndPointAddress
import net.geeksempire.geeky.gify.ViewModel.GifUserProfile
import org.json.JSONObject
import java.net.URL
import java.nio.charset.Charset

class RetrieveUserInformation {

    lateinit var jsonObjectImage: JSONObject

    lateinit var jsonObjectImageOriginal: JSONObject
    lateinit var jsonObjectImageOriginalLink: String

    lateinit var jsonObjectImagePreview: JSONObject
    lateinit var jsonObjectImagePreviewLink: String

    var gifUserProfile: GifUserProfile? = null

    fun getData(gifId: String) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val rawData = URL(
            EndPointAddress()
                .generateGiphyUserData(gifId)).readText(Charset.defaultCharset())

        val jsonObject : JSONObject = JSONObject(rawData)

        val gifJson: JSONObject = jsonObject.getJSONObject(GiphyJsonDataStructure.DATA)

        jsonObjectImage = gifJson.getJSONObject(GiphyJsonDataStructure.DATA_IMAGES)

        jsonObjectImageOriginal = jsonObjectImage.getJSONObject(GiphyJsonDataStructure.DATA_IMAGES_ORIGINAL)
        jsonObjectImageOriginalLink = jsonObjectImageOriginal.getString(GiphyJsonDataStructure.DATA_IMAGES_URL)

        jsonObjectImagePreview = jsonObjectImage.getJSONObject(GiphyJsonDataStructure.DATA_IMAGES_PREVIEW_GIF)
        jsonObjectImagePreviewLink = jsonObjectImagePreview.getString(GiphyJsonDataStructure.DATA_IMAGES_URL)

        if (!gifJson.isNull(GiphyJsonDataStructure.DATA_USER)) {
            val useJsonObject = gifJson.getJSONObject(GiphyJsonDataStructure.DATA_USER)
            gifUserProfile = GifUserProfile(userName = useJsonObject.getString(GiphyJsonDataStructure.DATA_USER_NAME),
                userAvatarUrl = useJsonObject.getString(GiphyJsonDataStructure.DATA_USER_AVATAR_URL),
                isUserVerified = useJsonObject.getBoolean(GiphyJsonDataStructure.DATA_USER_IS_VERIFIED))
        }
    }
}