/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 7:42 AM
 * Last modified 2/4/20 7:42 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.geekygify.BrowseGifCategory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.giphy.sdk.ui.GPHSettings
import com.giphy.sdk.ui.GiphyCoreUI
import com.giphy.sdk.ui.themes.DarkTheme
import com.giphy.sdk.ui.themes.GridType
import com.giphy.sdk.ui.views.GiphyDialogFragment
import net.geeksempire.geeky.geekygify.R

class BrowseGifCategoryView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        GiphyCoreUI.configure(this, getString(R.string.GIPHY_SDK_API_KEY))


        var giphySettings = GPHSettings(gridType = GridType.waterfall, theme = DarkTheme, dimBackground = true)

        GiphyDialogFragment.newInstance(giphySettings).showNow(supportFragmentManager, "GIF_DIALOGUE")
    }
}