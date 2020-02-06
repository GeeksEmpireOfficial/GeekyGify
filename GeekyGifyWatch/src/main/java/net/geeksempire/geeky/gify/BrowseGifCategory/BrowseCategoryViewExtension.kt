/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 9:51 AM
 * Last modified 2/6/20 9:05 AM
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
import net.geeksempire.geeky.gify.R

fun BrowseCategoryView.createViewModelObserver() : BrowseCategoryViewModel {

    categoryList.layoutManager = WearableLinearLayoutManager(applicationContext, BrowseCategoryWearLayoutManager())
    categoryList.isEdgeItemsCenteringEnabled = true
    categoryList.apply {
        isCircularScrollingGestureEnabled = true
        bezelFraction = 0.5f
        scrollDegreesPerScreen = 90f
    }

    val browseGifCategoryView = ViewModelProvider(this@createViewModelObserver).get(BrowseCategoryViewModel::class.java)

    browseGifCategoryView.categoriesListData.observe(this@createViewModelObserver,
        Observer {
            val categoryAdapter = BrowseCategoryAdapter(applicationContext, it)

            categoryList.adapter = categoryAdapter
            categoryAdapter.notifyDataSetChanged()
        })

    val categoriesNames = resources.getStringArray(R.array.gifCategoryList).toList() as ArrayList<String>

    browseGifCategoryView.setupCategoryBrowserData(categoriesNames)

    return browseGifCategoryView
}