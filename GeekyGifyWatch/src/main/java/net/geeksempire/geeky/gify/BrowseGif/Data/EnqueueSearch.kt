/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 11:27 AM
 * Last modified 2/7/20 11:27 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Data

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.Utils.Giphy.GiphySearchParameter
import net.geeksempire.geeky.gify.Utils.Giphy.SearchAddress
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources
import org.json.JSONObject
import javax.net.ssl.HttpsURLConnection

class EnqueueSearch {

    fun giphyJsonObjectRequest(context: Context, giphySearchParameter: GiphySearchParameter, browseGifViewModel: BrowseGifViewModel) {

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            SearchAddress().generateGiphySearchLink(giphySearchParameter),
            null,
            Response.Listener<JSONObject?> { response ->
                Log.d("JsonObjectRequest", response?.getJSONObject(GiphyJsonDataStructure.META)?.getInt(GiphyJsonDataStructure.META_STATUS).toString())

                if (response != null
                    && (response.getJSONObject(GiphyJsonDataStructure.META).getInt(GiphyJsonDataStructure.META_STATUS) == HttpsURLConnection.HTTP_OK)) {

                    browseGifViewModel.setupGifsBrowserData(response, GetResources(context).getNeonColors())
                }

            }, Response.ErrorListener {
                Log.d("JsonObjectRequest Error", it.toString())
            })

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonObjectRequest)
    }
}