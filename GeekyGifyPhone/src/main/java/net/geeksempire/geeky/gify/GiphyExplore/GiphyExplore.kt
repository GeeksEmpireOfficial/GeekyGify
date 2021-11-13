/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel on 11/13/21, 11:01 AM
 * Last modified 11/13/21, 10:42 AM
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
import com.giphy.sdk.ui.Giphy
import com.giphy.sdk.ui.themes.GPHTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GiphyDialogFragment
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.SharedDataController.DataController
import net.geeksempire.geeky.gify.SharedDataController.NullDataController
import net.geeksempire.geeky.gify.Utils.UI.SnackbarInteraction
import net.geeksempire.geeky.gify.Utils.UI.SnackbarView

class GiphyExplore {

    companion object {
        private const val GIPHY_SDK_API_KEY = "vaQ2LAuOJoWDVdtTYqdPHPI38PUzdnG1"
    }

    fun invokeGiphyExplore(nullDataController: NullDataController) {
        Giphy.configure((nullDataController.activity as AppCompatActivity), GiphyExplore.GIPHY_SDK_API_KEY, verificationMode = false)

        val giphySettings = GPHSettings(gridType = GridType.waterfall, theme = GPHTheme.Dark)
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

            override fun onGifSelected(media: Media, searchTerm: String?, selectedContentType: GPHContentType) {
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

            override fun didSearchTerm(term: String) {

            }

            override fun onDismissed(selectedContentType: GPHContentType) {

                SnackbarView()
                    .snackBarViewSuccess((nullDataController.requireContext()), nullDataController.nullDataControllerBinding.mainViewNullDataController, nullDataController.getString(R.string.explore), nullDataController.getString(R.string.nullData),
                        object : SnackbarInteraction {
                            override fun onActionClick() {

                                giphyDialog.showNow((nullDataController.activity as AppCompatActivity).supportFragmentManager, "GIF_DIALOGUE")

                            }
                        })

            }
        }
    }

    private fun generateGiphyExploreLink(gifMediaId: String) = "https://media.giphy.com/media/${gifMediaId}/giphy.gif"

    private fun generateGiphyExplorePreviewLink(gifMediaId: String) = "https://media1.giphy.com/media/${gifMediaId}/giphy-preview.gif"
}