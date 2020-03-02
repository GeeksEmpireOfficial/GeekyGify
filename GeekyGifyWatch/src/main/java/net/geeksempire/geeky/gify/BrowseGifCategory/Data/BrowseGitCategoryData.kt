/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/2/20 12:21 AM
 * Last modified 3/1/20 11:50 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Data

import android.content.Context
import android.util.Log
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import net.geeksempire.geeky.gify.BrowseGifCategory.RoomDatabase.GifCategoryDataInterface
import net.geeksempire.geeky.gify.BrowseGifCategory.RoomDatabase.GifCategoryDataModel
import net.geeksempire.geeky.gify.BrowseGifCategory.RoomDatabase.GifCategoryDatabase
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Data.CategoryListItemType
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils.BrowseGifCategoryType
import net.geeksempire.geeky.gify.GifFavorite.Util.FavoriteCheckpoint
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation
import net.geeksempire.geeky.gify.Utils.Calculations.numberEven

class BrowseGitCategoryData(var context: Context) {

    private val gifCategoryDataInterface: GifCategoryDataInterface = GifCategoryDatabase(context).initialGifCategoryDatabase()

    fun categoryListNames() : Deferred<ArrayList<CategoryListItemType>> = CoroutineScope(Dispatchers.IO).async {
        var gifCategoryList = ArrayList<CategoryListItemType>()

        gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_SEARCH, context.getString(R.string.searchGif)))
        gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_NULL, null))

        if (context.getDatabasePath(DatabaseInformation.GIF_CATEGORY_DATABASE_NAME).exists()) {
            Log.d(this@BrowseGitCategoryData.javaClass.simpleName, "Get Database Category List")

            if (FavoriteCheckpoint(context).favoriteDatabaseExists()) {
                gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_FAVORITE, context.getString(R.string.favoriteGif)))
                gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_NULL, null))
            }

            getGifCategoryDatabase().await().forEachIndexed { index, aString ->

                gifCategoryList.add(CategoryListItemType(
                    BrowseGifCategoryType.GIF_ITEM_CATEGORIES,
                    aString
                ))
            }

        } else {
            Log.d(this@BrowseGitCategoryData.javaClass.simpleName, "Get Default Category List")

            context.resources.getStringArray(R.array.gifCategoryList).forEachIndexed { index, aString ->
                gifCategoryList.add(CategoryListItemType(
                    BrowseGifCategoryType.GIF_ITEM_CATEGORIES,
                    aString))
            }

            initializeGifCategoryDatabase(gifCategoryList)
        }

        if (numberEven(gifCategoryList.size)) {

            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW, BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW))
            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_NULL, null))

            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_TRENDING, BrowseGifCategoryType.GIF_ITEM_TRENDING))
            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_NULL, null))

            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA, BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA))
            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_NULL, null))

        } else {

            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_NULL, null))
            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW, BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW))

            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_NULL, null))
            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_TRENDING, BrowseGifCategoryType.GIF_ITEM_TRENDING))

            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_NULL, null))
            gifCategoryList.add(CategoryListItemType(BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA, BrowseGifCategoryType.GIF_ITEM_SOCIAL_MEDIA))

        }


        gifCategoryList
    }

    private fun getGifCategoryDatabase() : Deferred<List<String>> = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        gifCategoryDataInterface.initDataAccessObject().getAllGifCategoryNames()
    }

    private suspend fun initializeGifCategoryDatabase(initialGifCategoryNames: ArrayList<CategoryListItemType>) {

        val arrayOfGifCategoryDataModels = ArrayList<GifCategoryDataModel>()

        initialGifCategoryNames.asFlow()
            .onEach {
                Log.d(this@BrowseGitCategoryData.javaClass.simpleName, "Database No Filter: ${it}")

            }
            .filter { it ->

                (it != null)
                        && (it.itemType != BrowseGifCategoryType.GIF_ITEM_FAVORITE)
                        && (it.itemType != BrowseGifCategoryType.GIF_ITEM_SEARCH)
                        && (it.itemType != BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD_NEW)
            }
            .map {

                it
            }
            .onCompletion {

                gifCategoryDataInterface.close()
            }
            .withIndex().collect {
                Log.d(this@BrowseGitCategoryData.javaClass.simpleName, "Database Filtered: ${it.value}")

                it.value.itemTitle?.let {  categoryName ->
                    arrayOfGifCategoryDataModels.add(
                        GifCategoryDataModel(
                            categoryName,
                            (initialGifCategoryNames.size - it.index).toLong()
                        )
                    )
                }
            }

        gifCategoryDataInterface.initDataAccessObject().insertAllNewGifCategoryData(arrayOfGifCategoryDataModels)
    }
}