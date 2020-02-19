/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/18/20 6:56 PM
 * Last modified 2/18/20 6:51 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Extension

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.ResultReceiver
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.wear.widget.WearableLinearLayoutManager
import com.google.android.wearable.intent.RemoteIntent
import kotlinx.android.synthetic.main.browse_gif_category_view.*
import kotlinx.coroutines.*
import net.geeksempire.geeky.gify.BrowseGifCategory.AddCategory.AddNewCategory
import net.geeksempire.geeky.gify.BrowseGifCategory.Data.BrowseGitCategoryData
import net.geeksempire.geeky.gify.BrowseGifCategory.RoomDatabase.GifCategoryDataInterface
import net.geeksempire.geeky.gify.BrowseGifCategory.RoomDatabase.GifCategoryDataModel
import net.geeksempire.geeky.gify.BrowseGifCategory.RoomDatabase.GifCategoryDatabase
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryAdapter
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.BrowseCategoryWearLayoutManager
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemData
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemDataLeft
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemDataRight
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

    val recyclerViewItemLongPress = object : RecyclerViewGifCategoryItemPress {

        override fun itemPressed(rightLeft: Boolean, categoryName: String, viewType: Int) {

            when (viewType) {
                BrowseGifCategoryType.GIF_ITEM_SEARCH_TYPE -> {

                    fragmentPlaceHolder.visibility = View.VISIBLE
                    GiphyExplore()
                        .invokeGiphyExplore(this@createViewModelObserver)

                }
                BrowseGifCategoryType.GIF_ITEM_FAVORITE_TYPE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES_TYPE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_TYPE -> {

                    fragmentPlaceHolder.visibility = View.VISIBLE

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
                        .replace(R.id.fragmentPlaceHolder, addNewCategory, "Add New Category")
                        .commit()

                }
                BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA_TYPE -> {

                    when (rightLeft) {
                        RecyclerViewRightLeftItem.RIGHT_ITEM -> {

                        }
                        RecyclerViewRightLeftItem.LEFT_ITEM -> {

                        }
                    }
                }
            }
        }

        override fun itemPressed(rightLeft: Boolean, viewType: Int) {
            when (viewType) {
                BrowseGifCategoryType.GIF_ITEM_SEARCH_TYPE -> {

                    fragmentPlaceHolder.visibility = View.VISIBLE
                    GiphyExplore()
                        .invokeGiphyExplore(this@createViewModelObserver)

                }
                BrowseGifCategoryType.GIF_ITEM_FAVORITE_TYPE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES_TYPE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_TYPE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA_TYPE -> {

                    when (rightLeft) {
                        RecyclerViewRightLeftItem.RIGHT_ITEM -> {

                            val resultReceiver = object : ResultReceiver(Handler()) {
                                override fun onReceiveResult(resultCode: Int, resultData: Bundle?) {
                                    if (resultCode == RemoteIntent.RESULT_OK) {


                                    } else if (resultCode == RemoteIntent.RESULT_FAILED) {

                                    }
                                }
                            }

                            val remoteIntent = Intent(Intent.ACTION_VIEW)
                                .addCategory(Intent.CATEGORY_BROWSABLE)
                                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                                .setData(Uri.parse(getString(R.string.facebookPageLink)))

                            RemoteIntent.startRemoteActivity(
                                applicationContext,
                                remoteIntent,
                                resultReceiver)
                        }
                        RecyclerViewRightLeftItem.LEFT_ITEM -> {

                            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.playStoreLink) + packageName)).apply {
                                startActivity(this)
                            }
                        }
                    }
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

        override suspend fun deleteCategory(rightLeft: Boolean, itemPosition: Int, categoryName: String) {

            var categoryItemDataLeft: CategoryItemDataLeft? = null
            var categoryItemDataRight: CategoryItemDataRight? = null

            categoryAdapter?.categoryItemsData?.get(itemPosition)?.let { pairCategory ->
                categoryItemDataLeft = pairCategory.categoryLeft
                categoryItemDataRight = pairCategory.categoryRight
            }

            when (rightLeft) {
                RecyclerViewRightLeftItem.RIGHT_ITEM -> {

                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

                        GifCategoryDatabase(applicationContext).initialGifCategoryDatabase()
                            .initDataAccessObject()
                            .deleteByCategoryName(categoryName)

                        categoryItemDataRight = null
                    }
                }
                RecyclerViewRightLeftItem.LEFT_ITEM -> {

                    CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

                        GifCategoryDatabase(applicationContext).initialGifCategoryDatabase()
                            .initDataAccessObject()
                            .deleteByCategoryName(categoryName)

                        categoryItemDataLeft = null
                    }
                }
            }

            delay(200)
            categoryAdapter?.categoryItemsData
                ?.set(itemPosition,
                    CategoryItemData(categoryItemDataLeft, categoryItemDataRight))

            withContext(Dispatchers.Main) {
                categoryAdapter?.
                        notifyItemChanged(itemPosition, CategoryItemData(categoryItemDataLeft, categoryItemDataRight))
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