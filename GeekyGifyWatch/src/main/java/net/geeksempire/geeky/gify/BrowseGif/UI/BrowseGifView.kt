/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/19/20 3:13 PM
 * Last modified 2/19/20 2:53 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif.UI

import android.os.Bundle
import android.util.Log
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.wear.ambient.AmbientModeSupport
import kotlinx.android.synthetic.main.browse_gif_list_view.*
import net.geeksempire.geeky.gify.BrowseGif.Extension.createClickListeners
import net.geeksempire.geeky.gify.BrowseGif.Extension.createViewModelObserver
import net.geeksempire.geeky.gify.BrowseGif.ViewModel.BrowseGifViewModel
import net.geeksempire.geeky.gify.GeekyGifyWatchApplication
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.NetworkConnectionListener
import net.geeksempire.geeky.gify.Utils.UI.DisplayDetection
import net.geeksempire.geeky.gify.Utils.UI.ShapeDetection
import javax.inject.Inject

class BrowseGifView : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider {

    private lateinit var ambientController: AmbientModeSupport.AmbientController

    val gifViewer: Fragment = GifViewer(null)

    @Inject
    lateinit var networkConnectionListener: NetworkConnectionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_gif_list_view)

        (application as GeekyGifyWatchApplication)
            .dependencyGraph
            .subDependencyGraph()
            .create(this@BrowseGifView, mainView)
            .inject(this@BrowseGifView)

        ambientController = AmbientModeSupport.attach(this)
        ambientController.setAmbientOffloadEnabled(true)

        val categoryName = intent.getStringExtra("CategoryName")

        categoryName?.let {

            categoryTitle.text = it

            val browseGifViewModel: BrowseGifViewModel = createViewModelObserver(it)

            createClickListeners(it, browseGifViewModel)

            categoryTitle.setOnClickListener {

            }
        }
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

                        exploreGifs.visibility = View.GONE
                    }
                    DisplayDetection.DisplayType.RECTANGLE -> {
                        Log.d(DisplayDetection::class.java.simpleName, "RECTANGLE")

                        val categoryTitleLayoutParameter: ViewGroup.LayoutParams = categoryTitle.layoutParams
                        categoryTitleLayoutParameter.width = ViewGroup.LayoutParams.MATCH_PARENT
                        categoryTitle.layoutParams = categoryTitleLayoutParameter
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