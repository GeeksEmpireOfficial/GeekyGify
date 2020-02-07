/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 4:29 PM
 * Last modified 2/6/20 4:29 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.Data

class GiphyJsonDataStructure {
    companion object {
        const val META = "meta"
        const val META_STATUS = "status"
        const val META_MSG = "msg"

        const val DATA = "data"

        const val DATA_IMAGES = "images"
        const val DATA_ORIGINAL = "original"
        const val DATA_PREVIEW_GIF = "preview_gif"
        const val DATA_URL = "url"
    }
}