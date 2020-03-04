/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 9:55 AM
 * Last modified 3/4/20 9:55 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.ViewModel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.asFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.CollectionSectionUI.Utils.CollectionFile
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import java.io.File

class BrowseGifViewModel : ViewModel() {

    val gifsListDataTrending: MutableLiveData<ArrayList<BrowseTrendingGifItemData>> by lazy {
        MutableLiveData<ArrayList<BrowseTrendingGifItemData>>()
    }

    val gifsListDataCollection: MutableLiveData<ArrayList<BrowseCollectionGifItemData>> by lazy {
        MutableLiveData<ArrayList<BrowseCollectionGifItemData>>()
    }

    val gifsListError: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    fun setupCollectionGifsBrowseData(rawDataFiles: ArrayList<File>, colorsList: ArrayList<String>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

        try {
            val browseGifItemData = ArrayList<BrowseCollectionGifItemData>()

            rawDataFiles.asFlow()
                .collect {
                    Log.d(this@BrowseGifViewModel.javaClass.simpleName, it.name)

                    val gifDrawable: File = it
                    val gifId: String = CollectionFile().extractGifId(it.name)
                    val aBackgroundColor = colorsList.random()

                    browseGifItemData.add(BrowseCollectionGifItemData(
                        gifDrawable,
                        gifId,
                        aBackgroundColor)
                    )
                }

            gifsListDataCollection.postValue(browseGifItemData)

        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun setupTrendingGifsBrowserData(rawDataJsonObject: JSONObject, colorsList: ArrayList<String>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

        try {
            val gifJsonArray: JSONArray = rawDataJsonObject.getJSONArray(GiphyJsonDataStructure.DATA)

            val browseGifItemData = ArrayList<BrowseTrendingGifItemData>()

            for (i in 0 until gifJsonArray.length()) {
                val jsonObject : JSONObject = gifJsonArray[i] as JSONObject

                val linkToGif = jsonObject.getString(GiphyJsonDataStructure.DATA_URL)

                val jsonObjectImage = jsonObject.getJSONObject(GiphyJsonDataStructure.DATA_IMAGES)

                val jsonObjectImageOriginal= jsonObjectImage.getJSONObject(GiphyJsonDataStructure.DATA_IMAGES_ORIGINAL)
                val jsonObjectImageOriginalLink = jsonObjectImageOriginal.getString(
                    GiphyJsonDataStructure.DATA_IMAGES_URL)

                val jsonObjectImagePreview = jsonObjectImage.getJSONObject(GiphyJsonDataStructure.DATA_IMAGES_PREVIEW_GIF)
                val jsonObjectImagePreviewLink = jsonObjectImagePreview.getString(
                    GiphyJsonDataStructure.DATA_IMAGES_URL)

                val aBackgroundColor = colorsList.random()
                browseGifItemData.add(
                    BrowseTrendingGifItemData(linkToGif,
                        jsonObjectImagePreviewLink,
                        jsonObjectImageOriginalLink,
                        if (!jsonObject.isNull(GiphyJsonDataStructure.DATA_USER)) {
                            val useJsonObject = jsonObject.getJSONObject(GiphyJsonDataStructure.DATA_USER)
                            GifUserProfile(userName = useJsonObject.getString(GiphyJsonDataStructure.DATA_USER_NAME),
                                userAvatarUrl = useJsonObject.getString(GiphyJsonDataStructure.DATA_USER_AVATAR_URL),
                                isUserVerified = useJsonObject.getBoolean(GiphyJsonDataStructure.DATA_USER_IS_VERIFIED))
                        } else {
                            null
                        },
                        aBackgroundColor)
                )
            }

            gifsListDataTrending.postValue(browseGifItemData)

        } catch (e: JSONException) {
            e.printStackTrace()
        }
    }
}