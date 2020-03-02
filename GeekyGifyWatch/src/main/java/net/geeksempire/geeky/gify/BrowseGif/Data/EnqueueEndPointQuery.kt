/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/2/20 4:50 AM
 * Last modified 3/2/20 4:50 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Data

import android.content.Context
import android.util.Log
import com.android.volley.DefaultRetryPolicy
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.GiphyExplore.EndPointAddress
import net.geeksempire.geeky.gify.GiphyExplore.GiphySearchParameter
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.JsonRequestResponseInterface
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources
import org.json.JSONObject
import javax.net.ssl.HttpsURLConnection


class EnqueueEndPointQuery {

    companion object {
        const val JSON_REQUEST_TIMEOUT = (1000 * 3)
        const val JSON_REQUEST_RETRIES = (3)
    }

    fun giphyJsonObjectRequest(context: Context,
                               giphySearchParameter: GiphySearchParameter,
                               jsonRequestResponseInterface: JsonRequestResponseInterface) = CoroutineScope(Dispatchers.IO).launch {

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            if (giphySearchParameter.queryType == BrowseGifViewModel.QUERY_TYPE.QUERY_SEARCH) {
                EndPointAddress().generateGiphySearchLink(giphySearchParameter)
            } else if (giphySearchParameter.queryType == BrowseGifViewModel.QUERY_TYPE.QUERY_TREND) {
                giphySearchParameter.requestLimit = 20
                EndPointAddress().generateGiphyTrendingLink(giphySearchParameter)
            } else {
                EndPointAddress().generateGiphySearchLink(giphySearchParameter)
            },
            null,
            Response.Listener<JSONObject?> { response ->
                Log.d("JsonObjectRequest", response?.getJSONObject(GiphyJsonDataStructure.META)?.getInt(GiphyJsonDataStructure.META_STATUS).toString())

                if (response != null
                    && (response.getJSONObject(GiphyJsonDataStructure.META).getInt(GiphyJsonDataStructure.META_STATUS) == HttpsURLConnection.HTTP_OK)) {

                    jsonRequestResponseInterface.jsonRequestResponseSuccessHandler(response, GetResources(context).getNeonColors())
                }

            }, Response.ErrorListener {
                Log.d("JsonObjectRequestError", it.toString())

                jsonRequestResponseInterface.jsonRequestResponseFailureHandler(it.toString())
            })

        jsonObjectRequest.retryPolicy = DefaultRetryPolicy(
            EnqueueEndPointQuery.JSON_REQUEST_TIMEOUT,
            EnqueueEndPointQuery.JSON_REQUEST_RETRIES,
            DefaultRetryPolicy.DEFAULT_BACKOFF_MULT
        )

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonObjectRequest)
    }
}