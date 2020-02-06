/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 11:14 AM
 * Last modified 2/6/20 11:10 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel

import android.content.Context
import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemData
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemDataLeft
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemDataRight
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.Calculations.numberEven

class BrowseCategoryViewModel : ViewModel() {

    val categoriesListData: MutableLiveData<ArrayList<CategoryItemData>> by lazy {
        MutableLiveData<ArrayList<CategoryItemData>>()
    }

    fun setupCategoryBrowserData(context: Context, rawData: ArrayList<String>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {
        val listOfColors = context.resources.getStringArray(R.array.neonColors).toList() as ArrayList<String>

        val categoriesListDataFinal = ArrayList<CategoryItemData>()

        val categoriesNamesLeft = ArrayList<CategoryItemDataLeft>()
        val categoriesNamesRight = ArrayList<CategoryItemDataRight>()

        rawData.forEachIndexed { index, aString ->
            if (numberEven(index)) {
                val colorData = listOfColors.random()
                val aBackgroundColor = Color.parseColor(colorData)
                categoriesNamesLeft.add(CategoryItemDataLeft(aString, aBackgroundColor))

                listOfColors.remove(colorData)
            } else {
                val colorData = listOfColors.random()
                val aBackgroundColor = Color.parseColor(colorData)
                categoriesNamesRight.add(CategoryItemDataRight(aString, aBackgroundColor))

                listOfColors.remove(colorData)
            }
        }

        for (it in categoriesNamesLeft.indices) {
            categoriesListDataFinal.add(
                CategoryItemData(try { categoriesNamesLeft[it] } catch (e: IndexOutOfBoundsException) { null } ,
                    try { categoriesNamesRight[it] } catch (e: IndexOutOfBoundsException) { null })
            )
        }

        categoriesListData.postValue(categoriesListDataFinal)
    }
}