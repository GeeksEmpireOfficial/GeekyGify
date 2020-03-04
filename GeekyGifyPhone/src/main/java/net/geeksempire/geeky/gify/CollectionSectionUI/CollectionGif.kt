/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/4/20 11:11 AM
 * Last modified 3/4/20 11:10 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.CollectionSectionUI

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.collection_gif.*
import kotlinx.android.synthetic.main.collection_gif.mainView
import kotlinx.android.synthetic.main.null_data_controller.*
import kotlinx.android.synthetic.main.shared_data_controller.*
import kotlinx.coroutines.*
import net.geeksempire.geeky.gify.BrowseGif.Data.GiphyJsonDataStructure
import net.geeksempire.geeky.gify.BrowseGif.Utils.RecyclerViewGifBrowseItemPress
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.ServerConnection.DownloadData.UserData.RetrieveUserInformation
import net.geeksempire.geeky.gify.SharedDataController.NullDataController
import net.geeksempire.geeky.gify.Utils.Calculations.DpToPixel
import net.geeksempire.geeky.gify.Utils.Calculations.rowCount
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources
import net.geeksempire.geeky.gify.Utils.UI.SnackbarInteraction
import net.geeksempire.geeky.gify.Utils.UI.SnackbarView
import net.geeksempire.geeky.gify.ViewModel.BrowseGifViewModel
import java.io.File

class CollectionGif(var nullDataController: NullDataController) {

    val context = nullDataController.context!!

    fun initial() = CoroutineScope(SupervisorJob() + Dispatchers.Main).launch {
        delay(1357)

        nullDataController.collectionList.layoutManager = GridLayoutManager(
            context,
            rowCount(
                nullDataController.collectionGifInclude.height,
                DpToPixel(105f, context).toInt()
            ),
            RecyclerView.HORIZONTAL, false
        )

        val browseGifViewModel =
            ViewModelProvider(nullDataController).get(BrowseGifViewModel::class.java)

        val geekyGifyCollectionFolder = File(context.externalMediaDirs[0].path + File.separator + "SharedGifCollection")
        if (geekyGifyCollectionFolder.exists()) {

            geekyGifyCollectionFolder.listFiles()?.let {

                browseGifViewModel.setupCollectionGifsBrowseData(
                    it.toList() as ArrayList<File>,
                    GetResources(context).getNeonColors()
                )
            }
        }

        val recyclerViewGifBrowseItemPressHandler: RecyclerViewGifBrowseItemPress =
            object : RecyclerViewGifBrowseItemPress {

                override fun itemPressedCollection(gifDrawable: File, gifId: String) {
                    nullDataController.activity!!.fragmentPlaceHolderGifViewer.visibility = View.VISIBLE

                    with(RetrieveUserInformation()) {
                        CoroutineScope(Dispatchers.IO).launch {
                            this@with.getData(gifId).await()

                            nullDataController.gifViewer.arguments = Bundle().apply {
                                putString(GiphyJsonDataStructure.DATA_URL, this@with.jsonObjectImageOriginalLink)
                                putString(GiphyJsonDataStructure.DATA_IMAGES_PREVIEW_GIF, this@with.jsonObjectImagePreviewLink)
                                putString(GiphyJsonDataStructure.DATA_IMAGES_ORIGINAL,  this@with.jsonObjectImageOriginalLink)

                                this@with.gifUserProfile?.let { gifUserProfile ->
                                    putString(
                                        GiphyJsonDataStructure.DATA_USER_NAME,
                                        gifUserProfile.userName
                                    )
                                    putString(
                                        GiphyJsonDataStructure.DATA_USER_AVATAR_URL,
                                        gifUserProfile.userAvatarUrl
                                    )
                                    putBoolean(
                                        GiphyJsonDataStructure.DATA_USER_IS_VERIFIED,
                                        gifUserProfile.isUserVerified
                                    )
                                }
                            }

                            (nullDataController.activity as AppCompatActivity).supportFragmentManager
                                .beginTransaction()
                                .setCustomAnimations(R.anim.slide_from_right, 0)
                                .replace(
                                    R.id.fragmentPlaceHolderGifViewer,
                                    nullDataController.gifViewer,
                                    "GIF VIEWER"
                                )
                                .commit()
                        }
                    }
                }
            }

        val collectionGifAdapter: CollectionGifAdapter =
            CollectionGifAdapter(this@CollectionGif, recyclerViewGifBrowseItemPressHandler)

        browseGifViewModel.gifsListDataCollection.observe(nullDataController,
            Observer {

                collectionGifAdapter.collectionGifAdapterData.clear()
                collectionGifAdapter.collectionGifAdapterData.addAll(it)

                nullDataController.collectionList.adapter = collectionGifAdapter

            })

        browseGifViewModel.gifsListError.observe(nullDataController,
            Observer {
                SnackbarView().snackBarViewFail(context,
                    nullDataController.mainView,
                    context.getString(R.string.downloadErrorOccurred),
                    object : SnackbarInteraction {})
            })
    }
}