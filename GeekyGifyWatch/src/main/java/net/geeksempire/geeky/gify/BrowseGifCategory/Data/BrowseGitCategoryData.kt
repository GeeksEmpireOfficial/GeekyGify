/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 5:48 PM
 * Last modified 2/13/20 5:42 PM
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
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.Adapter.Utils.BrowseGifCategoryType
import net.geeksempire.geeky.gify.GifFavorite.Util.FavoriteCheckpoint
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation
import net.geeksempire.geeky.gify.Utils.Calculations.numberEven

class BrowseGitCategoryData(var context: Context) {

    private val gifCategoryDataInterface: GifCategoryDataInterface = GifCategoryDatabase(
        context
    ).initialGifCategoryDatabase()

    fun categoryListNames() : Deferred<ArrayList<String?>> = CoroutineScope(Dispatchers.IO).async {
        var gifCategoryList = ArrayList<String?>()

        gifCategoryList.add(context.getString(R.string.searchGif))
        gifCategoryList.add(null)

        if (context.getDatabasePath(DatabaseInformation.GIF_CATEGORY_DATABASE_NAME).exists()) {
            Log.d(this@BrowseGitCategoryData.javaClass.simpleName, "Get Database Category List")

            if (FavoriteCheckpoint(context).favoriteDatabaseExists()) {
                gifCategoryList.add(context.getString(R.string.favoriteGif))
                gifCategoryList.add(null)
            }
            gifCategoryList.addAll(getGifCategoryDatabase().await() as ArrayList<String>)

        } else {
            Log.d(this@BrowseGitCategoryData.javaClass.simpleName, "Get Default Category List")

            gifCategoryList.addAll((context.resources.getStringArray(R.array.gifCategoryList).toList() as ArrayList<String?>))

            initializeGifCategoryDatabase(gifCategoryList)
        }

        if (numberEven(gifCategoryList.size)) {
            gifCategoryList.add(context.getString(R.string.addNewCategory))
            gifCategoryList.add(null)
        } else {
            gifCategoryList.add(null)
            gifCategoryList.add(context.getString(R.string.addNewCategory))
        }


        gifCategoryList
    }

    private fun getGifCategoryDatabase() : Deferred<List<String>> = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        gifCategoryDataInterface.initDataAccessObject().getAllGifCategoryNames()
    }

    private suspend fun initializeGifCategoryDatabase(initialGifCategoryNames: ArrayList<String?>) {

        val arrayOfGifCategoryDataModels = ArrayList<GifCategoryDataModel>()

        initialGifCategoryNames.asFlow()
            .onEach {
                Log.d(this@BrowseGitCategoryData.javaClass.simpleName, "Database No Filter: ${it}")

            }
            .filter { it ->

                (it != null) && (it != BrowseGifCategoryType.GIF_ITEM_FAVORITE) && (it != BrowseGifCategoryType.GIF_ITEM_SEARCH) && (it != BrowseGifCategoryType.GIF_ITEM_CATEGORIES_ADD)
            }
            .map {

                it
            }
            .onCompletion {

                gifCategoryDataInterface.close()
            }
            .withIndex().collect {
                Log.d(this@BrowseGitCategoryData.javaClass.simpleName, "Database Filtered: ${it.value}")

                it.value?.let {  categoryName ->
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