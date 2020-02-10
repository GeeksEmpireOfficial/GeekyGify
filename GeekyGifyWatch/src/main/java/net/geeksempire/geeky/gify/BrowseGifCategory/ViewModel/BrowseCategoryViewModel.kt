/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/9/20 6:28 PM
 * Last modified 2/9/20 6:20 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel

import android.graphics.Color
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemData
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemDataLeft
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemDataRight
import net.geeksempire.geeky.gify.Utils.Calculations.numberEven

class BrowseCategoryViewModel : ViewModel() {

    val categoriesListData: MutableLiveData<ArrayList<CategoryItemData>> by lazy {
        MutableLiveData<ArrayList<CategoryItemData>>()
    }

    fun setupCategoryBrowserData(rawData: ArrayList<String>, colorsList: ArrayList<String>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

        println(">>> raw " + rawData.size)

        val categoriesListDataFinal = ArrayList<CategoryItemData>()

        val categoriesNamesLeft = ArrayList<CategoryItemDataLeft>()
        val categoriesNamesRight = ArrayList<CategoryItemDataRight>()

        rawData.forEachIndexed { index, aString ->
            if (numberEven(index)) {
                val colorData = colorsList.random()
                val aBackgroundColor = Color.parseColor(colorData)
                categoriesNamesLeft.add(CategoryItemDataLeft(aString, aBackgroundColor))

                colorsList.remove(colorData)
            } else {
                val colorData = colorsList.random()
                val aBackgroundColor = Color.parseColor(colorData)
                categoriesNamesRight.add(CategoryItemDataRight(aString, aBackgroundColor))

                colorsList.remove(colorData)
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