/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/14/20 4:26 PM
 * Last modified 2/14/20 4:24 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.wear.ambient.AmbientModeSupport
import kotlinx.android.synthetic.main.browse_gif_category_view.*
import net.geeksempire.geeky.gify.BrowseGifCategory.Extension.createViewModelObserver
import net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel.BrowseCategoryViewModel
import net.geeksempire.geeky.gify.GeekyGifyWatchApplication
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.NetworkConnectionListener
import javax.inject.Inject

class BrowseCategoryView : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider  {

    private lateinit var ambientController: AmbientModeSupport.AmbientController

    private lateinit var browseGifCategoryView: BrowseCategoryViewModel

    @Inject
    lateinit var networkConnectionListener: NetworkConnectionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_gif_category_view)

        (application as GeekyGifyWatchApplication)
            .dependencyGraph
            .subDependencyGraph()
            .create(this@BrowseCategoryView, mainView)
            .inject(this@BrowseCategoryView)

        ambientController = AmbientModeSupport.attach(this)
        ambientController.setAmbientOffloadEnabled(true)

        browseGifCategoryView = createViewModelObserver()
    }

    override fun onDestroy() {
        super.onDestroy()

        networkConnectionListener.unRegisterDefaultNetworkCallback()
    }

    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback {

        return AmbientCallbackBrowseCategoryView()
    }

    private class AmbientCallbackBrowseCategoryView : AmbientModeSupport.AmbientCallback() {

        override fun onEnterAmbient(ambientDetails: Bundle?) {

        }

        override fun onExitAmbient() {

        }

        override fun onUpdateAmbient() {

        }
    }
}


