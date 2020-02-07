/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 4:27 PM
 * Last modified 2/6/20 4:24 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Extension

import android.content.Context
import net.geeksempire.geeky.gify.R

data class GiphySearchParameter(var categoryName: String, var requestLimit: Int = 10, var requestOffset: Int = 0, var searchRating: String = "G", var searchLanguage: String = "en")

class SearchAddress {

    fun generateSearchLink(context: Context, giphySearchParameter: GiphySearchParameter) : String{

        return "https://api.giphy.com/v1/gifs/search?" +
                "api_key=${context.getString(R.string.GIPHY_ENDPOINT_API_KEY)}" +
                "&q=${giphySearchParameter.categoryName}" +
                "&limit=${giphySearchParameter.requestLimit}" +
                "&offset=${giphySearchParameter.requestOffset}" +
                "&rating=${giphySearchParameter.searchRating}" +
                "&lang=${giphySearchParameter.searchLanguage}"
    }
}