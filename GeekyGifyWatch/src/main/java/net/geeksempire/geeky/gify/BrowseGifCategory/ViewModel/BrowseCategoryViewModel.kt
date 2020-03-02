/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/2/20 4:50 AM
 * Last modified 3/2/20 2:00 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel

import android.graphics.Color
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemData
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemDataLeft
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryItemDataRight
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryListItemType
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils.BrowseGifCategoryType
import net.geeksempire.geeky.gify.Utils.Calculations.numberEven

class BrowseCategoryViewModel : ViewModel() {

    companion object {
        val favoriteFirstLastModified: MutableLiveData<Boolean> by lazy {
            MutableLiveData<Boolean>()
        }
    }

    val categoriesListData: MutableLiveData<ArrayList<CategoryItemData>> by lazy {
        MutableLiveData<ArrayList<CategoryItemData>>()
    }

    fun setupCategoryBrowserData(rawData: ArrayList<CategoryListItemType>, colorsList: ArrayList<String>) = CoroutineScope(SupervisorJob() + Dispatchers.IO).launch {

        val categoriesListDataFinal = ArrayList<CategoryItemData>()

        val itemType = ArrayList<String>()
        val categoriesNamesLeft = ArrayList<CategoryItemDataLeft?>()
        val categoriesNamesRight = ArrayList<CategoryItemDataRight?>()

        rawData.forEachIndexed { index, categoryListItemType ->
            Log.d("CategoryRawData", "${categoryListItemType}")

            val colorData = colorsList.random()
            val aBackgroundColor = Color.parseColor(colorData)

            if (categoryListItemType.itemType == BrowseGifCategoryType.GIF_ITEM_SEARCH) {

                categoriesNamesLeft.add(CategoryItemDataLeft(categoryListItemType.itemTitle, 0))
                itemType.add(categoryListItemType.itemType)

            } else if (categoryListItemType.itemType == BrowseGifCategoryType.GIF_ITEM_FAVORITE) {

                categoriesNamesLeft.add(CategoryItemDataLeft(categoryListItemType.itemTitle, 0))
                itemType.add(categoryListItemType.itemType)

            } else if (categoryListItemType.itemType == BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW) {

                categoriesNamesLeft.add(CategoryItemDataLeft(categoryListItemType.itemTitle, aBackgroundColor))
                itemType.add(categoryListItemType.itemType)

            } else if (categoryListItemType.itemType == BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA) {

                categoriesNamesLeft.add(CategoryItemDataLeft(categoryListItemType.itemTitle, aBackgroundColor))
                itemType.add(categoryListItemType.itemType)

            } else if (categoryListItemType.itemType == BrowseGifCategoryType.GIF_ITEM_TRENDING) {

                categoriesNamesLeft.add(CategoryItemDataLeft(categoryListItemType.itemTitle, aBackgroundColor))
                itemType.add(categoryListItemType.itemType)

            }
            else if (categoryListItemType.itemType == BrowseGifCategoryType.GIF_ITEM_CATEGORIES) {
                if (numberEven(index)) {

                    categoriesNamesLeft.add(CategoryItemDataLeft(categoryListItemType.itemTitle, aBackgroundColor))
                    itemType.add(categoryListItemType.itemType)

                } else {

                    categoriesNamesRight.add(CategoryItemDataRight(categoryListItemType.itemTitle, aBackgroundColor))

                }
            } else if (categoryListItemType.itemType == BrowseGifCategoryType.GIF_ITEM_NULL) {

                categoriesNamesRight.add(null)

            }
        }

        for (it in categoriesNamesLeft.indices) {

            categoriesListDataFinal.add(
                CategoryItemData(
                    itemType[it],
                    try { categoriesNamesLeft[it] } catch (e: IndexOutOfBoundsException) { null } ,
                    try { categoriesNamesRight[it] } catch (e: IndexOutOfBoundsException) { null }
                )
            )
        }

        categoriesListData.postValue(categoriesListDataFinal)

        itemType.clear()
        categoriesNamesLeft.clear()
        categoriesNamesRight.clear()
    }
}