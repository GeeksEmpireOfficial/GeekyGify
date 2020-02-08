/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 3:48 PM
 * Last modified 2/7/20 2:34 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Giphy

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.core.models.enums.RatingType
import com.giphy.sdk.core.models.enums.RenditionType
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.GiphyCoreUI
import com.giphy.sdk.ui.themes.DarkTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GiphyDialogFragment
import kotlinx.android.synthetic.main.browse_gif_list_view.*
import net.geeksempire.geeky.gify.BrowseGif.UI.GifViewer
import net.geeksempire.geeky.gify.R

class GiphyExplore {

    companion object {
        private const val GIPHY_SDK_API_KEY = "vaQ2LAuOJoWDVdtTYqdPHPI38PUzdnG1"
    }

    fun invokeGiphyExplore(context: AppCompatActivity) {
        GiphyCoreUI.configure(context, GiphyExplore.GIPHY_SDK_API_KEY)

        val giphySettings = GPHSettings(gridType = GridType.waterfall, theme = DarkTheme, dimBackground = true)
        giphySettings.mediaTypeConfig = arrayOf(
            GPHContentType.gif,
            GPHContentType.sticker,
            GPHContentType.emoji,
            GPHContentType.text)
        giphySettings.rating = RatingType.unrated
        giphySettings.renditionType = RenditionType.preview
        giphySettings.confirmationRenditionType = RenditionType.original
        giphySettings.showAttribution = true

        val giphyDialog = GiphyDialogFragment.newInstance(giphySettings)
        giphyDialog.showNow(context.supportFragmentManager, "GIF_DIALOGUE")

        giphyDialog.gifSelectionListener = object: GiphyDialogFragment.GifSelectionListener {
            override fun onGifSelected(media: Media) {
                context.fragmentGifViewer.visibility = View.VISIBLE

                val gifViewer: Fragment = GifViewer()
                gifViewer.arguments = Bundle().apply {
                    putString("GIF_LINK", generateGiphyExploreLink(media.id))
                }

                context.supportFragmentManager
                    .beginTransaction()
                    .setCustomAnimations(R.anim.slide_from_right, 0)
                    .replace(R.id.fragmentGifViewer, gifViewer, "GIF VIEWER")
                    .commit()
            }

            override fun onDismissed() {

            }
        }
    }

    fun generateGiphyExploreLink(gifMediaId: String) = "https://media.giphy.com/media/${gifMediaId}/giphy.gif"
}