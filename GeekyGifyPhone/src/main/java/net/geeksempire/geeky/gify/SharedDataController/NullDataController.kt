/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/5/20 8:49 AM
 * Last modified 3/5/20 8:49 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.SharedDataController

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.null_data_controller.*
import net.geeksempire.geeky.gify.CollectionSectionUI.CollectionGif
import net.geeksempire.geeky.gify.CollectionSectionUI.Utils.CollectionFile
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.GifViewer.Utils.GifViewerFragmentStateListener
import net.geeksempire.geeky.gify.GifViewer.Utils.ReloadData
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.SharedDataController.Extension.setupClickNullDataControllerAdsApp
import net.geeksempire.geeky.gify.SharedDataController.Extension.setupNullDataControllerUI
import net.geeksempire.geeky.gify.TrendingSectionUI.TrendingGif
import net.geeksempire.geeky.gify.Utils.RetrieveResources.GetResources
import net.geeksempire.geeky.gify.ViewModel.BrowseCollectionGifItemData
import java.io.File

open class NullDataController : Fragment() {

    lateinit var collectionGif: CollectionGif
    lateinit var trendingGif: TrendingGif

    var gifViewer: Fragment = GifViewer(object : GifViewerFragmentStateListener {})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.null_data_controller, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        collectionGif = CollectionGif(this@NullDataController)
        trendingGif = TrendingGif(this@NullDataController)

        gifViewer = GifViewer(object : GifViewerFragmentStateListener {
            override fun onFragmentDetach(reloadDataType: ReloadData) {

                when (reloadDataType) {
                    ReloadData.DataType_Collection -> {

                        val geekyGifyCollectionFolder = File(context!!.externalMediaDirs[0].path + File.separator + "SharedGifCollection")
                        if (geekyGifyCollectionFolder.exists()) {

                            geekyGifyCollectionFolder.listFiles()?.let {

                                val browseGifItemData = ArrayList<BrowseCollectionGifItemData>()
                                val colorsList = GetResources(context!!).getNeonColors()

                                it.forEach {
                                    Log.d(this@NullDataController.javaClass.simpleName, it.name)

                                    val gifDrawable: File = it
                                    val gifId: String = CollectionFile().extractGifId(it.name)
                                    val aBackgroundColor = colorsList.random()

                                    browseGifItemData.add(BrowseCollectionGifItemData(
                                        gifDrawable,
                                        gifId,
                                        aBackgroundColor)
                                    )
                                }

                                collectionGif.updateData(browseGifItemData)
                            }
                        }
                    }
                    ReloadData.DataType_Trend -> {
                        TrendingGif(this@NullDataController).initial()
                    }
                }
            }
        })


        facebookIcon.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.facebookPageLink))).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
        }

        rateReviewIcon.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.playStoreLink) + context!!.packageName)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
        }

        setupClickNullDataControllerAdsApp()
        setupNullDataControllerUI()
    }
}