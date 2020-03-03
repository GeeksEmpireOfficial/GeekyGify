/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 2:00 AM
 * Last modified 3/3/20 2:00 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Data

import android.content.Context
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.ServerConnection.DownloadData.EndPointAddress
import net.geeksempire.geeky.gify.ServerConnection.DownloadData.GiphySearchParameter
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.JsonRequestResponseInterface
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
                EndPointAddress().generateGiphySearchLink(giphySearchParameter)
            } else if (giphySearchParameter.queryType == EndPointAddress.QUERY_TYPE.QUERY_TREND) {
                giphySearchParameter.requestLimit = 20

                EndPointAddress().generateGiphyTrendingLink(giphySearchParameter)
            } else {
                EndPointAddress().generateGiphySearchLink(giphySearchParameter)
            }
        ).readText(Charset.defaultCharset())
    }
}