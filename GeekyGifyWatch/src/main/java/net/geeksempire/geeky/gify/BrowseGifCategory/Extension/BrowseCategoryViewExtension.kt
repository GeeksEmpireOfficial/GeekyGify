/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 1:39 PM
 * Last modified 2/13/20 1:06 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Extension

import android.content.Context
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.wear.widget.WearableLinearLayoutManager
import kotlinx.android.synthetic.main.browse_gif_category_view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGifCategory.Data.BrowseGitCategoryData
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryAdapter
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryWearLayoutManager
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.RecyclerViewRightLeftItem
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils.BrowseGifCategoryType
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.BrowseCategoryView
import net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel.BrowseCategoryViewModel
import net.geeksempire.geeky.gify.GiphyExplore.GiphyExplore
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDataInterface
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDataModel
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDatabase
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources
import net.geeksempire.geeky.gify.Utils.UI.RecyclerViewGifCategoryItemPress


fun BrowseCategoryView.createViewModelObserver() : BrowseCategoryViewModel {


    categoryList.layoutManager = WearableLinearLayoutManager(applicationContext, BrowseCategoryWearLayoutManager())
    categoryList.isEdgeItemsCenteringEnabled = true
    //categoryList.isVerticalFadingEdgeEnabled = true
    categoryList.apply {
        this.isCircularScrollingGestureEnabled = true
        this.bezelFraction = 0.10f
        this.scrollDegreesPerScreen = 90f
    }

    var categoryAdapter: BrowseCategoryAdapter? = null

    val browseGifCategoryView = ViewModelProvider(this@createViewModelObserver).get(BrowseCategoryViewModel::class.java)

    val recyclerViewItemLongPress = object : RecyclerViewGifCategoryItemPress {
        override fun itemPressed(rightLeft: Boolean, categoryName: String, viewType: Int) {

            when (viewType) {
                BrowseGifCategoryType.GIF_ITEM_SEARCH -> {

                    GiphyExplore()
                        .invokeGiphyExplore(this@createViewModelObserver)

                }
                BrowseGifCategoryType.GIF_ITEM_FAVORITE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES -> {

                }
            }
        }

        override fun itemLongPressed(rightLeft: Boolean, categoryName: String, viewType: Int) {

            when (viewType) {
                BrowseGifCategoryType.GIF_ITEM_SEARCH -> {

                }
                BrowseGifCategoryType.GIF_ITEM_FAVORITE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES -> {

                    CoroutineScope(Dispatchers.IO).launch {
                        val gifCategoryDataInterface: GifCategoryDataInterface = GifCategoryDatabase(applicationContext).initialGifCategoryDatabase()

                        when (rightLeft) {
                            RecyclerViewRightLeftItem.RIGHT_ITEM -> {
                                gifCategoryDataInterface.initDataAccessObject().updateGifCategoryData(
                                    GifCategoryDataModel(categoryName, System.currentTimeMillis())
                                )
                            }
                            RecyclerViewRightLeftItem.LEFT_ITEM -> {
                                gifCategoryDataInterface.initDataAccessObject().updateGifCategoryData(
                                    GifCategoryDataModel(categoryName, System.currentTimeMillis())
                                )
                            }
                        }

                        triggerGifCategoryDataLoading(applicationContext, browseGifCategoryView)

                        gifCategoryDataInterface.close()
                    }

                }
            }
        }
    }

    browseGifCategoryView.categoriesListData.observe(this@createViewModelObserver,
        Observer {
            if (categoryAdapter == null) {

                categoryAdapter = BrowseCategoryAdapter(applicationContext, recyclerViewItemLongPress)
                categoryAdapter!!.categoryItemsData = it

                categoryList.adapter = categoryAdapter
                categoryAdapter?.notifyDataSetChanged()

                Handler().postDelayed({

                    categoryList
                        .smoothScrollToPosition(2)
                }, 99)
            } else {
                categoryAdapter?.let { categoryAdapter ->
                    categoryAdapter.categoryItemsData = it

                    categoryAdapter.notifyItemRangeChanged(0, categoryAdapter.itemCount)

                    Handler().postDelayed({

                        categoryList
                            .smoothScrollToPosition(2)
                    }, 99)
                }
            }
        })

    triggerGifCategoryDataLoading(applicationContext, browseGifCategoryView)

    return browseGifCategoryView
}

private fun triggerGifCategoryDataLoading(context: Context, browseGifCategoryView: BrowseCategoryViewModel) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
    BrowseGitCategoryData(context).categoryListNames().await().let {
        browseGifCategoryView.setupCategoryBrowserData(
            it,
            GetResources(context).getNeonColors()
        )
    }
}