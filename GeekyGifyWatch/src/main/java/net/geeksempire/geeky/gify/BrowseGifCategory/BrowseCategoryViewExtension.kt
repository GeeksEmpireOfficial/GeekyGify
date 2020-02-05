/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 3:48 PM
 * Last modified 2/4/20 3:47 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory

import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.wear.widget.WearableLinearLayoutManager
import kotlinx.android.synthetic.main.browse_gif_category_view.*
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.BrowseCategoryAdapter
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.BrowseCategoryWearLayoutManager
import net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel.BrowseCategoryViewModel

fun BrowseCategoryView.createViewModelObserver() : BrowseCategoryViewModel {

    categoryList.layoutManager = WearableLinearLayoutManager(applicationContext, BrowseCategoryWearLayoutManager())
    categoryList.isEdgeItemsCenteringEnabled = true
    categoryList.apply {
        isCircularScrollingGestureEnabled = true
        bezelFraction = 0.5f
        scrollDegreesPerScreen = 90f
    }

    val browseGifCategoryView = ViewModelProvider(this).get(BrowseCategoryViewModel::class.java)

    browseGifCategoryView.categoriesListData.observe(this,
        Observer {
            val categoryAdapter = BrowseCategoryAdapter(applicationContext, it)

            categoryList.adapter = categoryAdapter
            categoryAdapter.notifyDataSetChanged()
        })

    val categoriesNames = arrayListOf<String>("Fun", "Sport", "Boom", "Fuck", "Test", "Now", "Trend", "Extra", "FunX", "SportX", "BoomX", "FuckX", "TestX", "NowX", "TrendX")

    browseGifCategoryView.setupCategoryBrowserData(categoriesNames)

    return browseGifCategoryView
}