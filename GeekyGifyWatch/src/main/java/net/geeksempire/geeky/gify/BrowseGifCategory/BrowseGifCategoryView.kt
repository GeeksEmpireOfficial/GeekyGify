/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 3:21 PM
 * Last modified 2/4/20 3:16 PM
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
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemData
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemDataLeft
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemDataRight
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.WearLayoutManager
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.numberEven

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

        val lists = ArrayList<CategoryItemData>()

        val categoriesNames = arrayOf("Fun", "Sport", "Boom", "Fuck", "Test", "Now", "Trend", "Extra", "FunX", "SportX", "BoomX", "FuckX", "TestX", "NowX", "TrendX")

        val categoriesNamesLeft = ArrayList<CategoryItemDataLeft>()
        val categoriesNamesRight = ArrayList<CategoryItemDataRight>()


        CoroutineScope(Dispatchers.IO).launch {

            categoriesNames.forEachIndexed { index, aString ->
                if (numberEven(index)) {

                    categoriesNamesLeft.add(CategoryItemDataLeft(aString))

                } else {

                    categoriesNamesRight.add(CategoryItemDataRight(aString))

                }
            }

            for (it in categoriesNamesLeft.indices) {
                lists.add(
                    CategoryItemData(try { categoriesNamesLeft[it] } catch (e: IndexOutOfBoundsException) { null } ,
                        try { categoriesNamesRight[it] } catch (e: IndexOutOfBoundsException) { null })
                )

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