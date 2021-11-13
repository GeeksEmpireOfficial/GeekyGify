/*
 * Copyright © 2021 By Geeks Empire.
 *
 * Created by Elias Fazel on 11/13/21, 10:28 AM
 * Last modified 11/13/21, 10:27 AM
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
import android.os.Looper
import android.os.ResultReceiver
import android.util.Log
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.wear.widget.WearableLinearLayoutManager
import com.google.android.wearable.intent.RemoteIntent
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

    browseGifCategoryViewBinding.categoryList.layoutManager = WearableLinearLayoutManager(applicationContext, BrowseCategoryWearLayoutManager())
    browseGifCategoryViewBinding.categoryList.isEdgeItemsCenteringEnabled = true
    browseGifCategoryViewBinding.categoryList.apply {
        this.isCircularScrollingGestureEnabled = true
        this.bezelFraction = 0.10f
        this.scrollDegreesPerScreen = 90f
    }

    var categoryAdapter: BrowseCategoryAdapter? = null

    val browseGifCategoryView = ViewModelProvider(this@createViewModelObserver).get(BrowseCategoryViewModel::class.java)

    val recyclerViewItemLongPress = object : RecyclerViewGifCategoryItemPress {

        override fun itemPressed(rightLeft: RecyclerViewRightLeftItem, categoryName: String?, viewType: String) {

            when (viewType) {
                BrowseGifCategoryType.GIF_ITEM_SEARCH -> {

                    browseGifCategoryViewBinding.fragmentPlaceHolder.visibility = View.VISIBLE
                    GiphyExplore()
                        .invokeGiphyExplore(this@createViewModelObserver, browseGifCategoryViewBinding.fragmentPlaceHolder)

                }
                BrowseGifCategoryType.GIF_ITEM_FAVORITE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW -> {

                    browseGifCategoryViewBinding.fragmentPlaceHolder.visibility = View.VISIBLE

                    val addNewCategory: AddNewCategory = AddNewCategory().apply {
                        this.fragmentPlaceHolder = browseGifCategoryViewBinding.fragmentPlaceHolder
                    }
                    addNewCategory.gifCategoryFragmentStateListener = object : GifCategoryFragmentStateListener {
                        override fun onFragmentDetach() {
                            triggerGifCategoryDataLoading(applicationContext, browseGifCategoryView)
                        }
                    }

                    addNewCategory.arguments = Bundle().apply {

                    }

                    supportFragmentManager
                        .beginTransaction()
                        .setCustomAnimations(R.anim.slide_from_right, 0)
                        .replace(R.id.fragmentPlaceHolder, addNewCategory, "Add New Category")
                        .commit()

                }
                BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA -> {

                    when (rightLeft) {
                        RecyclerViewRightLeftItem.RightItem -> {

                            val resultReceiver = object : ResultReceiver(Handler((Looper.getMainLooper()))) {

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
                        RecyclerViewRightLeftItem.LeftItem -> {

                            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.playStoreLink) + packageName)).apply {
                                startActivity(this)
                            }
                        }
                    }
                }
            }
        }

        override fun itemLongPressed(rightLeft: RecyclerViewRightLeftItem, categoryName: String?, viewType: String) {

            when (viewType) {
                BrowseGifCategoryType.GIF_ITEM_SEARCH -> {

                }
                BrowseGifCategoryType.GIF_ITEM_FAVORITE -> {

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES -> {

                    CoroutineScope(Dispatchers.IO).launch {

                        val gifCategoryDataInterface: GifCategoryDataInterface = GifCategoryDatabase(applicationContext).initialGifCategoryDatabase()

                        when (rightLeft) {
                            RecyclerViewRightLeftItem.RightItem -> {
                                categoryName?.let {
                                    gifCategoryDataInterface.initDataAccessObject().updateGifCategoryData(
                                        GifCategoryDataModel(
                                            it,
                                            System.currentTimeMillis()
                                        )
                                    )
                                }
                            }
                            RecyclerViewRightLeftItem.LeftItem -> {
                                categoryName?.let {
                                    gifCategoryDataInterface.initDataAccessObject().updateGifCategoryData(
                                        GifCategoryDataModel(
                                            it,
                                            System.currentTimeMillis()
                                        )
                                    )
                                }
                            }
                        }

                        triggerGifCategoryDataLoading(applicationContext, browseGifCategoryView)

                        gifCategoryDataInterface.close()
                    }

                }
                BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW -> {

                }
            }
        }

        override suspend fun deleteCategory(rightLeft: RecyclerViewRightLeftItem, itemPosition: Int, categoryName: String) {

            categoryAdapter?.let { categoryAdapterIt ->
                var viewType: String = BrowseGifCategoryType.GIF_ITEM_CATEGORIES
                var categoryItemDataLeft: CategoryItemDataLeft? = null
                var categoryItemDataRight: CategoryItemDataRight? = null

                categoryAdapterIt.categoryItemsData?.get(itemPosition)?.let { pairCategory ->
                    viewType = pairCategory.viewType
                    categoryItemDataLeft = pairCategory.categoryLeft
                    categoryItemDataRight = pairCategory.categoryRight
                }

                when (rightLeft) {
                    RecyclerViewRightLeftItem.RightItem -> {

                        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

                            GifCategoryDatabase(applicationContext).initialGifCategoryDatabase()
                                .initDataAccessObject()
                                .deleteByCategoryName(categoryName)

                            categoryItemDataRight = null
                        }
                    }
                    RecyclerViewRightLeftItem.LeftItem -> {

                        CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

                            GifCategoryDatabase(applicationContext).initialGifCategoryDatabase()
                                .initDataAccessObject()
                                .deleteByCategoryName(categoryName)

                            categoryItemDataLeft = null
                        }
                    }
                }

                categoryAdapterIt.deleteCounter++

                delay(200)

                if (categoryAdapterIt.deleteCounter >= 5) {
                    categoryAdapterIt.deleteCounter = 0

                    triggerGifCategoryDataLoading(
                        applicationContext,
                        browseGifCategoryView
                    )

                } else {

                    categoryAdapterIt.categoryItemsData
                        .set(itemPosition,
                            CategoryItemData(
                                viewType,
                                categoryItemDataLeft,
                                categoryItemDataRight))

                    withContext(Dispatchers.Main) {
                        categoryAdapterIt.
                            notifyItemChanged(itemPosition, CategoryItemData(viewType, categoryItemDataLeft, categoryItemDataRight))
                    }
                }
            }
        }
    }

    browseGifCategoryView.categoriesListData.observe(this@createViewModelObserver,
        Observer {
            Log.d("BrowseGifCategoryView", "${it.size}")

            if (categoryAdapter == null) {

                categoryAdapter = BrowseCategoryAdapter(applicationContext, recyclerViewItemLongPress)
                categoryAdapter!!.categoryItemsData.clear()
                categoryAdapter!!.categoryItemsData.addAll(it)

                browseGifCategoryViewBinding.categoryList.adapter = categoryAdapter
                categoryAdapter?.notifyDataSetChanged()

                Handler(Looper.getMainLooper()).postDelayed({

                    browseGifCategoryViewBinding.categoryList
                        .smoothScrollToPosition(if(it.size > 4){ 2 } else { 0 })
                }, 99)

            } else {

                categoryAdapter!!.let { categoryAdapter ->
                    categoryAdapter.categoryItemsData.clear()
                    categoryAdapter.categoryItemsData.addAll(it)

                    categoryAdapter.notifyDataSetChanged()

                    Handler(Looper.getMainLooper()).postDelayed({

                        browseGifCategoryViewBinding.categoryList
                            .smoothScrollToPosition(if(it.size > 4){ 2 } else { 0 })
                    }, 99)
                }

            }
        })

    BrowseCategoryViewModel.favoriteFirstLastModified.observe(this@createViewModelObserver,
        Observer {
            if (it) {
                triggerGifCategoryDataLoading(
                    applicationContext,
                    browseGifCategoryView
                )
            }
        })

    triggerGifCategoryDataLoading(applicationContext, browseGifCategoryView)

    return browseGifCategoryView
}

private fun triggerGifCategoryDataLoading(context: Context, browseGifCategoryView: BrowseCategoryViewModel) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

    BrowseGitCategoryData(context).categoryListNames().await().let { listDataCategory ->

        browseGifCategoryView.setupCategoryBrowserData(
            listDataCategory,
            GetResources(context).getNeonColors()
        )
    }
}