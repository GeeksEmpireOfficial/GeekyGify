/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 6/19/20 2:46 PM
 * Last modified 6/19/20 2:46 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.UI

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.wear.ambient.AmbientModeSupport
import com.bumptech.glide.Glide
import net.geeksempire.geeky.gify.BrowseGif.Extension.createClickListeners
import net.geeksempire.geeky.gify.BrowseGif.Extension.createViewModelObserver
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.GeekyGifyWatchApplication
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.NetworkConnectionListener
import net.geeksempire.geeky.gify.Utils.UI.DisplayDetection
import net.geeksempire.geeky.gify.Utils.UI.ShapeDetection
import net.geeksempire.geeky.gify.databinding.BrowseGifListViewBinding
import javax.inject.Inject

class BrowseGifView : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider {

    private lateinit var ambientController: AmbientModeSupport.AmbientController

    val gifViewer: GifViewer by lazy {
        GifViewer().apply {
            this.fragmentPlaceHolder = browseGifListViewBinding.fragmentPlaceHolder
        }
    }

    lateinit var browseGifListViewBinding: BrowseGifListViewBinding

    @Inject
    lateinit var networkConnectionListener: NetworkConnectionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        browseGifListViewBinding = BrowseGifListViewBinding.inflate(layoutInflater)
        setContentView(browseGifListViewBinding.root)

        (application as GeekyGifyWatchApplication)
            .dependencyGraph
            .subDependencyGraph()
            .create(this@BrowseGifView, browseGifListViewBinding.mainView)
            .inject(this@BrowseGifView)

        ambientController = AmbientModeSupport.attach(this)
        ambientController.setAmbientOffloadEnabled(true)

        val queryType: String = intent.getStringExtra("QueryType")!!
        val categoryName: String = intent.getStringExtra("CategoryName")!!

        browseGifListViewBinding.categoryTitle.text = categoryName

        val browseGifViewModel: BrowseGifViewModel = createViewModelObserver(queryType, categoryName)

        createClickListeners(queryType, categoryName, browseGifViewModel)

        browseGifListViewBinding.categoryTitle.setOnClickListener {

        }

        val animatable = getDrawable(R.drawable.animated_search_icon) as Animatable
        animatable.start()

        Glide.with(applicationContext)
            .load(animatable as Drawable)
            .into(browseGifListViewBinding.exploreGifs)
    }

    override fun onResume() {
        super.onResume()

        val displayDetection = DisplayDetection(this@BrowseGifView)
        displayDetection.initializeShapeDetection(object : ShapeDetection {

            override fun shapeType(typeOfShape: Int) {

                when (typeOfShape) {
                    DisplayDetection.DisplayType.ROUND_FULL -> {
                        Log.d(DisplayDetection::class.java.simpleName, "ROUND")

                    }
                    DisplayDetection.DisplayType.ROUND_CHIN -> {
                        Log.d(DisplayDetection::class.java.simpleName, "CHIN")

                        browseGifListViewBinding.exploreGifs.visibility = View.GONE
                    }
                    DisplayDetection.DisplayType.RECTANGLE -> {
                        Log.d(DisplayDetection::class.java.simpleName, "RECTANGLE")

                        val categoryTitleLayoutParameter: ViewGroup.LayoutParams = browseGifListViewBinding.categoryTitle.layoutParams
                        categoryTitleLayoutParameter.width = ViewGroup.LayoutParams.MATCH_PARENT
                        browseGifListViewBinding.categoryTitle.layoutParams = categoryTitleLayoutParameter
                    }
                }
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()

        networkConnectionListener.unRegisterDefaultNetworkCallback()
    }

    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback {

        return AmbientCallbackBrowseGifView()
    }

    private class AmbientCallbackBrowseGifView : AmbientModeSupport.AmbientCallback() {

        override fun onEnterAmbient(ambientDetails: Bundle?) {

        }

        override fun onExitAmbient() {

        }

        override fun onUpdateAmbient() {

        }
    }
}