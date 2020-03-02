/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/2/20 4:50 AM
 * Last modified 3/2/20 3:41 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GiphyExplore

import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel

data class GiphySearchParameter(var queryType: String = BrowseGifViewModel.gifQueryType,
                                var categoryName: String,
                                var requestLimit: Int = BrowseGifViewModel.gifRequestLimit,
                                var requestOffset: Int = BrowseGifViewModel.gifRequestOffset,
                                var searchRating: String = BrowseGifViewModel.gifRequestRating,
                                var searchLanguage: String = BrowseGifViewModel.gifRequestLanguage)

class EndPointAddress {

    companion object {
        private const val GIPHY_ENDPOINT_API_KEY = "uvO9uLD5yDRHZ1NgI6zOroJKLsJbvNDQ"
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