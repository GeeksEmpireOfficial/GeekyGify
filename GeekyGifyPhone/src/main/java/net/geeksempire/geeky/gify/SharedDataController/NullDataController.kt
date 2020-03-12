/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/11/20 4:31 PM
 * Last modified 3/11/20 4:13 PM
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
import net.geeksempire.geeky.gify.databinding.NullDataControllerBinding
import java.io.File

open class NullDataController : Fragment() {

    lateinit var nullDataControllerBinding: NullDataControllerBinding

    lateinit var collectionGif: CollectionGif
    lateinit var trendingGif: TrendingGif

    var gifViewer: Fragment = GifViewer(object : GifViewerFragmentStateListener {})

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(layoutInflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        nullDataControllerBinding = NullDataControllerBinding.inflate(layoutInflater, container, false)

        return nullDataControllerBinding.root
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

                                it.forEach { file ->
                                    Log.d(this@NullDataController.javaClass.simpleName, file.name)

                                    val gifDrawable: File = file
                                    val gifId: String = CollectionFile().extractGifId(file.name)
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

        nullDataControllerBinding.facebookIcon.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.facebookPageLink))).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
        }

        nullDataControllerBinding.rateReviewIcon.setOnClickListener {
            Intent(Intent.ACTION_VIEW, Uri.parse(getString(R.string.playStoreLink) + requireContext().packageName)).apply {
                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                startActivity(this)
            }
        }

        setupClickNullDataControllerAdsApp()
        setupNullDataControllerUI()
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}