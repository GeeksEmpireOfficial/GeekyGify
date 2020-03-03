/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 4:54 AM
 * Last modified 3/3/20 4:15 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Networking.ServerConnections

import android.content.Context
import net.geeksempire.geeky.gify.ServerConnection.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources
import org.json.JSONObject

class JsonRequestResponse {

    fun jsonRequestResponseHandler(context: Context, browseGifViewModel: BrowseGifViewModel): JsonRequestResponseInterface {
        return object : JsonRequestResponseInterface {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonObject: JSONObject, colorsList: ArrayList<String>) {

                browseGifViewModel.setupGifsBrowserData(rawDataJsonObject,
                    GetResources(context).getNeonColors())
            }

            override fun jsonRequestResponseFailureHandler(jsonError: String?) {

                browseGifViewModel.gifsListError.postValue(jsonError)
            }
        }
    }
}