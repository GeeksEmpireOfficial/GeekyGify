/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/5/20 8:08 AM
 * Last modified 3/5/20 8:01 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.ServerConnection.DownloadData.EndPoint.EndPointAddress
import net.geeksempire.geeky.gify.ServerConnection.DownloadData.EndPoint.GiphySearchParameter
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.JsonRequestResponseInterface
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources
import org.json.JSONObject
import java.net.URL
import java.nio.charset.Charset


class EnqueueEndPointQuery {

    companion object {
        const val JSON_REQUEST_TIMEOUT = (1000 * 3)
        const val JSON_REQUEST_RETRIES = (3)
    }

    fun giphyJsonObjectRequest(context: Context,
                               giphySearchParameter: GiphySearchParameter,
                               jsonRequestResponseInterface: JsonRequestResponseInterface) = CoroutineScope(Dispatchers.IO).launch {


        val retrievedData: String = URL(
            if (giphySearchParameter.queryType == EndPointAddress.QUERY_TYPE.QUERY_SEARCH) {
                EndPointAddress()
                    .generateGiphySearchLink(giphySearchParameter)
            } else if (giphySearchParameter.queryType == EndPointAddress.QUERY_TYPE.QUERY_TREND) {
                giphySearchParameter.requestLimit = 31

                EndPointAddress()
                    .generateGiphyTrendingLink(giphySearchParameter)
            } else {
                EndPointAddress()
                    .generateGiphySearchLink(giphySearchParameter)
            }
        ).readText(Charset.defaultCharset())

        if (retrievedData.isEmpty()) {
            jsonRequestResponseInterface.jsonRequestResponseFailureHandler(null)
        } else {
            jsonRequestResponseInterface.jsonRequestResponseSuccessHandler(
                JSONObject(retrievedData),
                GetResources(context).getNeonColors()
            )
        }
    }
}