/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 5/16/20 1:24 PM
 * Last modified 5/16/20 12:55 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GiphyExplore

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
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
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.R

class GiphyExplore {

    companion object {
        private const val GIPHY_SDK_API_KEY = "vaQ2LAuOJoWDVdtTYqdPHPI38PUzdnG1"
    }

    fun invokeGiphyExplore(appCompatActivity: AppCompatActivity) {
        Giphy.configure(appCompatActivity, GiphyExplore.GIPHY_SDK_API_KEY, verificationMode = false)

        val giphySettings = GPHSettings(gridType = GridType.waterfall, theme = GPHTheme.Dark, useBlurredBackground = true)
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
        giphyDialog.showNow(appCompatActivity.supportFragmentManager, "GIF_DIALOGUE")

        giphyDialog.gifSelectionListener = object: GiphyDialogFragment.GifSelectionListener {

            override fun onGifSelected(media: Media, searchTerm: String?) {

                val gifViewer: Fragment = GifViewer()
                gifViewer.arguments = Bundle().apply {
                    putString(GiphyJsonDataStructure.DATA_URL, media.url)
                    putString(GiphyJsonDataStructure.DATA_IMAGES_PREVIEW_GIF, generateGiphyExplorePreviewLink(media.id))
                    putString(GiphyJsonDataStructure.DATA_IMAGES_ORIGINAL, generateGiphyExploreLink(media.id))

                    media.user?.let { gifUserProfile ->

                        putString(GiphyJsonDataStructure.DATA_USER_NAME, gifUserProfile.username)
                        putString(GiphyJsonDataStructure.DATA_USER_AVATAR_URL, gifUserProfile.avatarUrl)
                        putBoolean(GiphyJsonDataStructure.DATA_USER_IS_VERIFIED, gifUserProfile.verified)
                    }
                }

                appCompatActivity.supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, 0)
                    .replace(R.id.fragmentPlaceHolder, gifViewer, "GIF VIEWER")
                    .commit()
            }

            override fun didSearchTerm(term: String) {

            }

            override fun onDismissed() {

            }
        }
    }

    private fun generateGiphyExploreLink(gifMediaId: String) = "https://media.giphy.com/media/${gifMediaId}/giphy.gif"

    private fun generateGiphyExplorePreviewLink(gifMediaId: String) = "https://media1.giphy.com/media/${gifMediaId}/giphy-preview.gif"
}