/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 5:43 PM
 * Last modified 2/10/20 5:42 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Extension

import android.content.Context
import android.os.Handler
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.room.Room
import androidx.wear.widget.WearableLinearLayoutManager
import kotlinx.android.synthetic.main.browse_gif_category_view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGifCategory.Data.BrowseGitCategoryData
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryAdapter
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryWearLayoutManager
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.RecyclerViewRightLeftItem
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.BrowseCategoryView
import net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel.BrowseCategoryViewModel
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseNames
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDataInterface
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDataModel
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources
import net.geeksempire.geeky.gify.Utils.UI.RecyclerViewGifCategoryItemLongPress


fun BrowseCategoryView.createViewModelObserver() : BrowseCategoryViewModel {

    categoryList.layoutManager = WearableLinearLayoutManager(applicationContext, BrowseCategoryWearLayoutManager())
    categoryList.isEdgeItemsCenteringEnabled = true
    categoryList.apply {
        isCircularScrollingGestureEnabled = true
        bezelFraction = 0.5f
        scrollDegreesPerScreen = 90f
    }

    var categoryAdapter: BrowseCategoryAdapter? = null

    val browseGifCategoryView = ViewModelProvider(this@createViewModelObserver).get(BrowseCategoryViewModel::class.java)

    val recyclerViewItemLongPress = object : RecyclerViewGifCategoryItemLongPress {
        override fun itemLongPressed(rightLeft: Boolean, categoryName: String) {

            CoroutineScope(Dispatchers.IO).launch {
                val gifCategoryDataInterface = Room.databaseBuilder(applicationContext, GifCategoryDataInterface::class.java, DatabaseNames.GIF_CATEGORY_DATABASE_NAME)
                    .build()

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

    browseGifCategoryView.categoriesListData.observe(this@createViewModelObserver,
        Observer {
            if (categoryAdapter == null) {

                categoryAdapter = BrowseCategoryAdapter(applicationContext, recyclerViewItemLongPress)
                categoryAdapter!!.categoryItemsData = it

                categoryList.adapter = categoryAdapter
                categoryAdapter?.notifyDataSetChanged()
            } else {
                categoryAdapter?.let { categoryAdapter ->
                    categoryAdapter.categoryItemsData = it

                    categoryAdapter.notifyItemRangeChanged(0, categoryAdapter.itemCount)

                    Handler().postDelayed({

                        categoryList
                            .smoothScrollToPosition(0)
                    }, 99)
                }
            }
        })

    triggerGifCategoryDataLoading(applicationContext, browseGifCategoryView)

    return browseGifCategoryView
}

private fun triggerGifCategoryDataLoading(context: Context, browseGifCategoryView: BrowseCategoryViewModel) {
    CoroutineScope(Dispatchers.IO).launch {
        BrowseGitCategoryData().categoryListNames(context).await().let {
            browseGifCategoryView.setupCategoryBrowserData(
                it,
                GetResources(context).getNeonColors()
            )
        }
    }
}