/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/11/20 5:52 PM
 * Last modified 3/11/20 5:25 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GiphyExplore

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.core.models.enums.RatingType
import com.giphy.sdk.core.models.enums.RenditionType
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.GiphyCoreUI
import com.giphy.sdk.ui.themes.DarkTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GiphyDialogFragment
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.SharedDataController.DataController
import net.geeksempire.geeky.gify.SharedDataController.NullDataController

class GiphyExplore {

    companion object {
        private const val GIPHY_SDK_API_KEY = "vaQ2LAuOJoWDVdtTYqdPHPI38PUzdnG1"
    }

    fun invokeGiphyExplore(nullDataController: NullDataController) {
        GiphyCoreUI.configure((nullDataController.activity as AppCompatActivity), GiphyExplore.GIPHY_SDK_API_KEY, verificationMode = true)

        val giphySettings = GPHSettings(gridType = GridType.waterfall, theme = DarkTheme, dimBackground = true)
        giphySettings.mediaTypeConfig = arrayOf(
            GPHContentType.gif,
            GPHContentType.sticker,
            GPHContentType.emoji,
            GPHContentType.text)
        giphySettings.rating = RatingType.g
        giphySettings.renditionType = RenditionType.preview
        giphySettings.confirmationRenditionType = RenditionType.original
        giphySettings.showAttribution = true

        val giphyDialog = GiphyDialogFragment.newInstance(giphySettings)
        giphyDialog.showNow((nullDataController.activity as AppCompatActivity).supportFragmentManager, "GIF_DIALOGUE")

        giphyDialog.gifSelectionListener = object: GiphyDialogFragment.GifSelectionListener {

            override fun onGifSelected(media: Media) {
                (nullDataController.activity as DataController).dataControllerBinding.fragmentPlaceHolderGifViewer.visibility = View.VISIBLE

                nullDataController.gifViewer.arguments = Bundle().apply {
                    putString(GiphyJsonDataStructure.DATA_URL, media.url)
                    putString(GiphyJsonDataStructure.DATA_IMAGES_PREVIEW_GIF, generateGiphyExplorePreviewLink(media.id))
                    putString(GiphyJsonDataStructure.DATA_IMAGES_ORIGINAL, generateGiphyExploreLink(media.id))

                    media.user?.let { gifUserProfile ->

                        putString(GiphyJsonDataStructure.DATA_USER_NAME, gifUserProfile.username)
                        putString(GiphyJsonDataStructure.DATA_USER_AVATAR_URL, gifUserProfile.avatarUrl)
                        putBoolean(GiphyJsonDataStructure.DATA_USER_IS_VERIFIED, gifUserProfile.verified)
                    }
                }

                (nullDataController.activity as AppCompatActivity).supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, 0)
                    .replace(R.id.fragmentPlaceHolderGifViewer, nullDataController.gifViewer, "GIF VIEWER")
                    .commit()
            }

            override fun onDismissed() {

            }
        }
    }

    private fun generateGiphyExploreLink(gifMediaId: String) = "https://media.giphy.com/media/${gifMediaId}/giphy.gif"

    private fun generateGiphyExplorePreviewLink(gifMediaId: String) = "https://media1.giphy.com/media/${gifMediaId}/giphy-preview.gif"
}