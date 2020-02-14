/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 7:41 PM
 * Last modified 2/13/20 7:38 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Extension

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.wear.widget.WearableLinearLayoutManager
import kotlinx.android.synthetic.main.browse_gif_category_view.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGifCategory.AddCategory.AddNewCategory
import net.geeksempire.geeky.gify.BrowseGifCategory.Data.BrowseGitCategoryData
import net.geeksempire.geeky.gify.BrowseGifCategory.RoomDatabase.GifCategoryDataInterface
import net.geeksempire.geeky.gify.BrowseGifCategory.RoomDatabase.GifCategoryDataModel
import net.geeksempire.geeky.gify.BrowseGifCategory.RoomDatabase.GifCategoryDatabase
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryAdapter
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryWearLayoutManager
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.RecyclerViewRightLeftItem
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils.BrowseGifCategoryType
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.BrowseCategoryView
import net.geeksempire.geeky.gify.BrowseGifCategory.Utils.GifCategoryFragmentStateListener
import net.geeksempire.geeky.gify.BrowseGifCategory.Utils.RecyclerViewGifCategoryItemPress
import net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel.BrowseCategoryViewModel
import net.geeksempire.geeky.gify.GiphyExplore.GiphyExplore
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources

fun BrowseCategoryView.createViewModelObserver() : BrowseCategoryViewModel {


    categoryList.layoutManager = WearableLinearLayoutManager(applicationContext, BrowseCategoryWearLayoutManager())
    categoryList.isEdgeItemsCenteringEnabled = true
    categoryList.apply {
        this.isCircularScrollingGestureEnabled = true
        this.bezelFraction = 0.10f
        this.scrollDegreesPerScreen = 90f
    }

    var categoryAdapter: BrowseCategoryAdapter? = null

    val browseGifCategoryView = ViewModelProvider(this@createViewModelObserver).get(BrowseCategoryViewModel::class.java)

    val recyclerViewItemLongPress = object :
        RecyclerViewGifCategoryItemPress {
        override fun itemPressed(rightLeft: Boolean, categoryName: String, viewType: Int) {

            when (viewType) {
                BrowseGifCategoryType.GIF_ITEM_SEARCH_TYPE -> {

                    GiphyExplore()
                        .invokeGiphyExplore(this@createViewModelObserver)

                }
                BrowseGifCategoryType.GIF_ITEM_FAVORITE_TYPE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES_TYPE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_TYPE -> {

                    fragmentNewCategory.visibility = View.VISIBLE

                    val addNewCategory: Fragment = AddNewCategory(object : GifCategoryFragmentStateListener {
                        override fun onFragmentDetach() {
                            triggerGifCategoryDataLoading(applicationContext, browseGifCategoryView)
                        }
                    })

                    addNewCategory.arguments = Bundle().apply {

                    }

                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_right, 0)
                        .replace(R.id.fragmentNewCategory, addNewCategory, "Add New Category")
                        .commit()

                }
            }
        }

        override fun itemLongPressed(rightLeft: Boolean, categoryName: String, viewType: Int) {

            when (viewType) {
                BrowseGifCategoryType.GIF_ITEM_SEARCH_TYPE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_FAVORITE_TYPE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES_TYPE -> {

                    CoroutineScope(Dispatchers.IO).launch {
                        val gifCategoryDataInterface: GifCategoryDataInterface = GifCategoryDatabase(
                            applicationContext
                        ).initialGifCategoryDatabase()

                        when (rightLeft) {
                            RecyclerViewRightLeftItem.RIGHT_ITEM -> {
                                gifCategoryDataInterface.initDataAccessObject().updateGifCategoryData(
                                    GifCategoryDataModel(
                                        categoryName,
                                        System.currentTimeMillis()
                                    )
                                )
                            }
                            RecyclerViewRightLeftItem.LEFT_ITEM -> {
                                gifCategoryDataInterface.initDataAccessObject().updateGifCategoryData(
                                    GifCategoryDataModel(
                                        categoryName,
                                        System.currentTimeMillis()
                                    )
                                )
                            }
                        }

                        triggerGifCategoryDataLoading(applicationContext, browseGifCategoryView)

                        gifCategoryDataInterface.close()
                    }

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_TYPE -> {

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
                        .smoothScrollToPosition(if(it[0].categoryLeft?.categoryTitle == "Search"){ 2 } else { 0 })
                }, 99)
            } else {
                categoryAdapter?.let { categoryAdapter ->
                    categoryAdapter.categoryItemsData = it

                    categoryAdapter.notifyItemRangeChanged(0, categoryAdapter.itemCount)

                    Handler().postDelayed({

                        categoryList
                            .smoothScrollToPosition(if(it[0].categoryLeft?.categoryTitle == "Search"){ 2 } else { 0 })
                    }, 99)
                }
            }
        })

    BrowseCategoryViewModel.firstFavoriteAdded.observe(this@createViewModelObserver,
        Observer {
            triggerGifCategoryDataLoading(
                applicationContext,
                browseGifCategoryView
            )

            BrowseCategoryViewModel.firstFavoriteAdded.removeObserver {/**/}
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