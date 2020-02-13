/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/12/20 5:55 PM
 * Last modified 2/12/20 5:43 PM
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

        if (context.getDatabasePath(DatabaseInformation.GIF_CATEGORY_DATABASE_NAME).exists()) {

            if (FavoriteCheckpoint(context).favoriteDatabaseExists()) {
                gifCategoryList.add(context.getString(R.string.favoriteTitle))
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
            .filter {

                (it != null)
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