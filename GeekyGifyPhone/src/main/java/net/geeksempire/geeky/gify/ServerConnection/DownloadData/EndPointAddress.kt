/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/3/20 2:00 AM
 * Last modified 3/3/20 1:32 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.ServerConnection.DownloadData


data class GiphySearchParameter(var queryType: String = EndPointAddress.gifQueryType,
                                var categoryName: String,
                                var requestLimit: Int = EndPointAddress.gifRequestLimit,
                                var requestOffset: Int = EndPointAddress.gifRequestOffset,
                                var searchRating: String = EndPointAddress.gifRequestRating,
                                var searchLanguage: String = EndPointAddress.gifRequestLanguage)

class EndPointAddress {

    companion object {
        var gifQueryType: String = EndPointAddress.QUERY_TYPE.QUERY_SEARCH
        const val gifRequestLimit: Int = 12
        var gifRequestOffset: Int = 0
        var gifRequestRating: String = "G"
        var gifRequestLanguage: String = "en"

        private const val GIPHY_ENDPOINT_API_KEY = "uvO9uLD5yDRHZ1NgI6zOroJKLsJbvNDQ"
    }

    object QUERY_TYPE {
        const val QUERY_SEARCH = "Search"
        const val QUERY_TREND = "Trend"
    }

    fun generateGiphySearchLink(giphySearchParameter: GiphySearchParameter) : String {

        return "https://api.giphy.com/v1/gifs/search?" +
                "api_key=${EndPointAddress.GIPHY_ENDPOINT_API_KEY}" +
                "&q=${giphySearchParameter.categoryName}" +
                "&limit=${giphySearchParameter.requestLimit}" +
                "&offset=${giphySearchParameter.requestOffset}" +
                "&rating=${giphySearchParameter.searchRating}" +
                "&lang=${giphySearchParameter.searchLanguage}"
    }

    fun generateGiphyTrendingLink(giphySearchParameter: GiphySearchParameter): String {

        return "https://api.giphy.com/v1/gifs/trending?" +
                "api_key=${EndPointAddress.GIPHY_ENDPOINT_API_KEY}" +
                "&limit=${giphySearchParameter.requestLimit}" +
                "&rating=${giphySearchParameter.searchRating}"
    }
}