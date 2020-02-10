/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/9/20 7:17 PM
 * Last modified 2/9/20 7:14 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.Data

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.RoomDatabase.DatabaseNames
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDataInterface
import net.geeksempire.geeky.gify.RoomDatabase.GifCategory.GifCategoryDataModel

class BrowseGitCategoryData {

    fun categoryListNames(context: Context) : Deferred<ArrayList<String>> = CoroutineScope(Dispatchers.IO).async {
        var gifCategoryList = ArrayList<String>()

        if (context.getDatabasePath(DatabaseNames.GIF_CATEGORY_DATABASE_NAME).exists()) {

            gifCategoryList = getGifCategoryDatabase(context).await() as ArrayList<String>

        } else {

            gifCategoryList = context.resources.getStringArray(R.array.gifCategoryList).toList() as ArrayList<String>

            initializeGifCategoryDatabase(context, gifCategoryList)

        }

        gifCategoryList
    }

    private fun getGifCategoryDatabase(context: Context) = CoroutineScope(SupervisorJob() + Dispatchers.IO).async {

        val gifCategoryDataInterface = Room.databaseBuilder(context, GifCategoryDataInterface::class.java, DatabaseNames.GIF_CATEGORY_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(supportSQLiteDatabase: SupportSQLiteDatabase) {
                    super.onCreate(supportSQLiteDatabase)
                }

                override fun onOpen(supportSQLiteDatabase: SupportSQLiteDatabase) {
                    super.onOpen(supportSQLiteDatabase)
                }
            })
            .build()

        gifCategoryDataInterface.initDataAccessObject().getAllGifCategoryNames()
    }

    private suspend fun initializeGifCategoryDatabase(context: Context, initialGifCategoryNames: ArrayList<String>) {

        val gifCategoryDataInterface = Room.databaseBuilder(context, GifCategoryDataInterface::class.java, DatabaseNames.GIF_CATEGORY_DATABASE_NAME)
            .fallbackToDestructiveMigration()
            .addCallback(object : RoomDatabase.Callback() {
                override fun onCreate(supportSQLiteDatabase: SupportSQLiteDatabase) {
                    super.onCreate(supportSQLiteDatabase)
                }

                override fun onOpen(supportSQLiteDatabase: SupportSQLiteDatabase) {
                    super.onOpen(supportSQLiteDatabase)
                }
            })
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