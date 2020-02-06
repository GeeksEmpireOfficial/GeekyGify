/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 9:51 AM
 * Last modified 2/6/20 8:50 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Giphy

import android.content.Context
import androidx.fragment.app.FragmentManager
import com.giphy.sdk.core.models.Media
import com.giphy.sdk.core.models.enums.RatingType
import com.giphy.sdk.core.models.enums.RenditionType
import com.giphy.sdk.ui.GPHContentType
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.GiphyCoreUI
import com.giphy.sdk.ui.themes.DarkTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GiphyDialogFragment
import net.geeksempire.geeky.gify.R

class GiphySearch {

    fun invokeGiphySearch(context: Context, fragmentManager: FragmentManager) {
        GiphyCoreUI.configure(context, context.getString(R.string.GIPHY_SDK_API_KEY))

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
        giphyDialog.showNow(fragmentManager, "GIF_DIALOGUE")


        giphyDialog.gifSelectionListener = object: GiphyDialogFragment.GifSelectionListener {
            //https://media.giphy.com/media/media.id/giphy.gif
            override fun onGifSelected(media: Media) {

                //  Glide.with(applicationContext).asGif().load("https://media.giphy.com/media/${media.id}/giphy.gif").into(gifViewer)

            }

            override fun onDismissed() {
                //Your user dismissed the dialog without selecting a GIF
            }
        }
    }
}