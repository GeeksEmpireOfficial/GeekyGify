/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 6:03 PM
 * Last modified 2/8/20 2:42 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GiphyExplore

import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel

data class GiphySearchParameter(var categoryName: String,
                                val requestLimit: Int = BrowseGifViewModel.gifRequestLimit,
                                var requestOffset: Int = BrowseGifViewModel.gifRequestOffset,
                                var searchRating: String = BrowseGifViewModel.gifRequestRating,
                                var searchLanguage: String = BrowseGifViewModel.gifRequestLanguage)

class SearchAddress {

    companion object {
        private const val GIPHY_ENDPOINT_API_KEY = "uvO9uLD5yDRHZ1NgI6zOroJKLsJbvNDQ"
    }

    fun generateGiphySearchLink(giphySearchParameter: GiphySearchParameter) : String{

        return "https://api.giphy.com/v1/gifs/search?" +
                "api_key=${SearchAddress.GIPHY_ENDPOINT_API_KEY}" +
                "&q=${giphySearchParameter.categoryName}" +
                "&limit=${giphySearchParameter.requestLimit}" +
                "&offset=${giphySearchParameter.requestOffset}" +
                "&rating=${giphySearchParameter.searchRating}" +
                "&lang=${giphySearchParameter.searchLanguage}"
    }
}