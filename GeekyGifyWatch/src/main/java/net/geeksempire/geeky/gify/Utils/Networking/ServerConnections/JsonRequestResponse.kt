/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/20/20 12:35 PM
 * Last modified 2/20/20 12:32 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Networking.ServerConnections

import android.content.Context
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources
import org.json.JSONObject

class JsonRequestResponse {

    fun jsonRequestResponseHandler(context: Context, browseGifViewModel: BrowseGifViewModel): JsonRequestResponseInterface {
        return object : JsonRequestResponseInterface {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonObject: JSONObject, colorsList: ArrayList<String>) {

                browseGifViewModel.setupGifsBrowserData(rawDataJsonObject,
                    GetResources(context).getNeonColors())
            }

            override fun jsonRequestResponseFailureHandler(jsonError: String) {

                browseGifViewModel.gifsListError.postValue(jsonError)
            }
        }
    }
}