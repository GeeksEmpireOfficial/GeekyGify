/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 3:48 PM
 * Last modified 2/4/20 3:47 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemData
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemDataLeft
import net.geeksempire.geeky.gify.BrowseGifCategory.Adapter.Data.CategoryItemDataRight
import net.geeksempire.geeky.gify.Utils.numberEven

class BrowseCategoryViewModel : ViewModel() {

    val categoriesListData: MutableLiveData<ArrayList<CategoryItemData>> by lazy {
        MutableLiveData<ArrayList<CategoryItemData>>()
    }

    fun setupCategoryBrowserData(rawData: ArrayList<String>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

        val categoriesListDataFinal = ArrayList<CategoryItemData>()

        val categoriesNamesLeft = ArrayList<CategoryItemDataLeft>()
        val categoriesNamesRight = ArrayList<CategoryItemDataRight>()

        rawData.forEachIndexed { index, aString ->
            if (numberEven(index)) {
                categoriesNamesLeft.add(CategoryItemDataLeft(aString))
            } else {
                categoriesNamesRight.add(CategoryItemDataRight(aString))
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