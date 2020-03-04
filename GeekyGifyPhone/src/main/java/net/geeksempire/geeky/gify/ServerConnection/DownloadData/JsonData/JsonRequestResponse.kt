/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 9:55 AM
 * Last modified 3/4/20 9:55 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Networking.ServerConnections

import android.content.Context
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources
import net.geeksempire.geeky.gify.ViewModel.BrowseGifViewModel
import org.json.JSONObject

class JsonRequestResponse {

    fun jsonRequestResponseHandler(context: Context, browseGifViewModel: BrowseGifViewModel): JsonRequestResponseInterface {
        return object : JsonRequestResponseInterface {

            override fun jsonRequestResponseSuccessHandler(rawDataJsonObject: JSONObject, colorsList: ArrayList<String>) {

                browseGifViewModel.setupTrendingGifsBrowserData(rawDataJsonObject,
                    GetResources(context).getNeonColors())
            }

            override fun jsonRequestResponseFailureHandler(jsonError: String?) {

                browseGifViewModel.gifsListError.postValue(jsonError)
            }
        }
    }
}