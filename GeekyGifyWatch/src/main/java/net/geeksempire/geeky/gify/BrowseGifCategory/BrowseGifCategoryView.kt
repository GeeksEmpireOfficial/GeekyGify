/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 2:53 PM
 * Last modified 2/4/20 2:50 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.wear.widget.WearableLinearLayoutManager
import kotlinx.android.synthetic.main.browse_gif_category_view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.CategoryAdapter
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.WearLayoutManager
import net.geeksempire.geeky.gify.BrowseGifCategory.Data.CategoryItemData
import net.geeksempire.geeky.gify.R

class BrowseGifCategoryView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_gif_category_view)

        /*GiphyCoreUI.configure(this, getString(R.string.GIPHY_SDK_API_KEY))

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
        giphyDialog.showNow(supportFragmentManager, "GIF_DIALOGUE")


        giphyDialog.gifSelectionListener = object: GiphyDialogFragment.GifSelectionListener {
            //https://media.giphy.com/media/media.id/giphy.gif
            override fun onGifSelected(media: Media) {

              //  Glide.with(applicationContext).asGif().load("https://media.giphy.com/media/${media.id}/giphy.gif").into(gifViewer)

            }

            override fun onDismissed() {
                //Your user dismissed the dialog without selecting a GIF
            }
        }*/


        val categoriesNamesLeft = arrayOf("Fun", "Sport", "Boom", "Fuck", "Test", "Now", "Trend", "Extra", "Something")
        val categoriesNamesRight = arrayOf("FunX", "SportX", "BoomX", "FuckX", "TestX", "NowX", "TrendX")

        val lists = ArrayList<CategoryItemData>()


        CoroutineScope(Dispatchers.IO).launch {

            for (it in categoriesNamesLeft.indices) {
                try {

                } catch (e: ArrayIndexOutOfBoundsException) {

                }
                lists.add(CategoryItemData(try { categoriesNamesLeft[it] } catch (e: ArrayIndexOutOfBoundsException) { null } , try { categoriesNamesRight[it] } catch (e: ArrayIndexOutOfBoundsException) { null }))

            }
        }

        val categoryAdapter = CategoryAdapter(applicationContext, lists)

        categoryList.layoutManager = WearableLinearLayoutManager(applicationContext, WearLayoutManager())
        categoryList.isEdgeItemsCenteringEnabled = true
        categoryList.apply {
            isCircularScrollingGestureEnabled = true
            bezelFraction = 0.5f
            scrollDegreesPerScreen = 90f
        }
        categoryList.adapter = categoryAdapter

    }
}