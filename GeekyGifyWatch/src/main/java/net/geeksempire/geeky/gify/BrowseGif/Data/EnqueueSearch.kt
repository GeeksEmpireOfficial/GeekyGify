/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 4:27 PM
 * Last modified 2/6/20 4:27 PM
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
import net.geeksempire.geeky.gify.BrowseGif.Adapter.Data.BrowseGifItemData
import net.geeksempire.geeky.gify.BrowseGif.Extension.GiphySearchParameter
import net.geeksempire.geeky.gify.BrowseGif.Extension.SearchAddress
import org.json.JSONArray
import org.json.JSONException
import org.json.JSONObject
import javax.net.ssl.HttpsURLConnection

class EnqueueSearch {

    private fun giphyJsonObjectRequest(context: Context, giphySearchParameter: GiphySearchParameter) {

        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET,
            SearchAddress().generateSearchLink(context, giphySearchParameter),
            null,
            Response.Listener<JSONObject?> { response ->
                println(">>> ${response}")

                if (response != null
                    && (response.getJSONObject(GiphyJsonDataStructure.META).getString(GiphyJsonDataStructure.META_STATUS).toInt() == HttpsURLConnection.HTTP_OK)) {

                    try {
                        val gifJsonArray: JSONArray = response.getJSONArray(GiphyJsonDataStructure.DATA)

                        val dataToAdapter = ArrayList<BrowseGifItemData>()

                        for (i in 0 until gifJsonArray.length()) {
                            val jsonObject : JSONObject = gifJsonArray[i] as JSONObject
                            val jsonObjectImage = jsonObject.getJSONObject("images")

                            val jsonObjectImageOriginal= jsonObjectImage.getJSONObject("original")
                            val jsonObjectImageOriginalLink = jsonObjectImageOriginal.getString("url")


                            val jsonObjectImagePreview = jsonObjectImage.getJSONObject("preview_gif")
                            val jsonObjectImagePreviewLink = jsonObjectImagePreview.getString("url")

                            dataToAdapter.add(BrowseGifItemData(jsonObjectImagePreviewLink, jsonObjectImageOriginalLink))

                        }


                    } catch (e: JSONException) {
                        e.printStackTrace()
                    }
                }

            }, Response.ErrorListener {
                Log.d("JsonObjectRequest Error", it.toString())
            })

        val requestQueue = Volley.newRequestQueue(context)
        requestQueue.add(jsonObjectRequest)
    }
}