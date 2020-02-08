/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 10:44 AM
 * Last modified 2/8/20 10:22 AM
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
        const val DATA_IMAGES_ORIGINAL = "original"
        const val DATA_IMAGES_PREVIEW_GIF = "preview_gif"
        const val DATA_IMAGES_URL = "url"

        const val DATA_USER = "user"
        const val DATA_USER_DISPLAY_NAME = "display_name"
        const val DATA_USER_AVATAR_URL = "avatar_url"
        const val DATA_USER_IS_VERIFIED = "is_verified"
    }
}