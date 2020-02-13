/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 10:33 AM
 * Last modified 2/13/20 9:58 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Data

import android.content.Context
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import net.geeksempire.geeky.gify.GifFavorite.Util.FavoriteCheckpoint
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDataInterface
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDataModel
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDatabase

class BrowseGitCategoryData(var context: Context) {

    private val gifCategoryDataInterface: GifCategoryDataInterface = GifCategoryDatabase(context).initialGifCategoryDatabase()

    fun categoryListNames() : Deferred<ArrayList<String?>> = CoroutineScope(Dispatchers.IO).async {
        var gifCategoryList = ArrayList<String?>()

        gifCategoryList.add(context.getString(R.string.searchGif))
        gifCategoryList.add(null)

        if (context.getDatabasePath(DatabaseInformation.GIF_CATEGORY_DATABASE_NAME).exists()) {

            if (FavoriteCheckpoint(context).favoriteDatabaseExists()) {
                gifCategoryList.add(context.getString(R.string.favoriteGif))
                gifCategoryList.add(null)
            }
            gifCategoryList.addAll(getGifCategoryDatabase().await() as ArrayList<String>)

        } else {

            gifCategoryList = context.resources.getStringArray(R.array.gifCategoryList).toList() as ArrayList<String?>

            initializeGifCategoryDatabase(gifCategoryList)

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

            }
            .filter { it ->

                (it != null) || (it == "Favorites") || (it == "Search")
            }
            .map {

                it
            }
            .onCompletion {

                gifCategoryDataInterface.close()
            }
            .withIndex().collect {

                it.value?.let {  categoryName ->
                    arrayOfGifCategoryDataModels.add(
                        GifCategoryDataModel(
                            categoryName,
                            (initialGifCategoryNames.size -  it.index).toLong()
                        )
                    )
                }
            }

        gifCategoryDataInterface.initDataAccessObject().insertAllNewGifCategoryData(arrayOfGifCategoryDataModels)
    }
}