/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 3:44 PM
 * Last modified 2/4/20 3:39 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.wear.widget.WearableLinearLayoutManager
import kotlinx.android.synthetic.main.browse_gif_category_view.*
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.CategoryAdapter
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.WearLayoutManager
import net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel.CategoryBrowserViewModel

fun BrowseGifCategoryView.createViewModelObserver() : CategoryBrowserViewModel {

    categoryList.layoutManager = WearableLinearLayoutManager(applicationContext, WearLayoutManager())
    categoryList.isEdgeItemsCenteringEnabled = true
    categoryList.apply {
        isCircularScrollingGestureEnabled = true
        bezelFraction = 0.5f
        scrollDegreesPerScreen = 90f
    }

    val browseGifCategoryView = ViewModelProvider(this).get(CategoryBrowserViewModel::class.java)

    browseGifCategoryView.categoriesListData.observe(this,
        Observer {
            val categoryAdapter = CategoryAdapter(applicationContext, it)

            categoryList.adapter = categoryAdapter
            categoryAdapter.notifyDataSetChanged()
        })

    val categoriesNames = arrayListOf<String>("Fun", "Sport", "Boom", "Fuck", "Test", "Now", "Trend", "Extra", "FunX", "SportX", "BoomX", "FuckX", "TestX", "NowX", "TrendX")

    browseGifCategoryView.setupCategoryBrowserData(categoriesNames)

    return browseGifCategoryView
}