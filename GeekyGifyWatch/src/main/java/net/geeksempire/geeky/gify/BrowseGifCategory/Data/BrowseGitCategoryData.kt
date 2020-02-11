/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/10/20 7:52 PM
 * Last modified 2/10/20 7:49 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Data

import android.content.Context
import androidx.room.Room
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseInformation
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDataInterface
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDataModel

class BrowseGitCategoryData(var context: Context) {

    fun categoryListNames() : Deferred<ArrayList<String>> = CoroutineScope(Dispatchers.IO).async {
        var gifCategoryList = ArrayList<String>()

        if (context.getDatabasePath(DatabaseInformation.GIF_CATEGORY_DATABASE_NAME).exists()) {

            gifCategoryList = getGifCategoryDatabase().await() as ArrayList<String>

        } else {

            gifCategoryList = context.resources.getStringArray(R.array.gifCategoryList).toList() as ArrayList<String>

            initializeGifCategoryDatabase(gifCategoryList)

        }

        gifCategoryList
    }

    private fun getGifCategoryDatabase() : Deferred<List<String>> = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val gifCategoryDataInterface = Room.databaseBuilder(context, GifCategoryDataInterface::class.java, DatabaseInformation.GIF_CATEGORY_DATABASE_NAME)
            .build()

        gifCategoryDataInterface.initDataAccessObject().getAllGifCategoryNames()
    }

    private suspend fun initializeGifCategoryDatabase(initialGifCategoryNames: ArrayList<String>) {

        val gifCategoryDataInterface = Room.databaseBuilder(context, GifCategoryDataInterface::class.java, DatabaseInformation.GIF_CATEGORY_DATABASE_NAME)
            .build()

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
                arrayOfGifCategoryDataModels.add(
                    GifCategoryDataModel(
                        it.value,
                        (initialGifCategoryNames.size -  it.index).toLong()
                    )
                )
            }

        gifCategoryDataInterface.initDataAccessObject().insertAllNewGifCategoryData(arrayOfGifCategoryDataModels)
    }
}