/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/13/20 3:50 PM
 * Last modified 2/13/20 3:25 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.wear.ambient.AmbientModeSupport
import kotlinx.android.synthetic.main.favorites_gif_list_view.*
import net.geeksempire.geeky.gify.GeekyGifyApplication
import net.geeksempire.geeky.gify.GifFavorite.Extension.favoritesGifViewObserverExtension
import net.geeksempire.geeky.gify.GifFavorite.ViewModel.FavoritesGifViewModel
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.GifViewer.Utils.GifViewerFragmentStateListener
import net.geeksempire.geeky.gify.GiphyExplore.GiphyExplore
import net.geeksempire.geeky.gify.R

class FavoritesGifView : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider {

    private lateinit var ambientController: AmbientModeSupport.AmbientController

    private lateinit var favoritesGifViewModel: FavoritesGifViewModel

    var gifViewer: Fragment = GifViewer(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites_gif_list_view)

        (application as GeekyGifyApplication)
            .dependencyGraph
            .subDependencyGraph()
            .create(this@FavoritesGifView, mainView)
            .inject(this@FavoritesGifView)

        ambientController = AmbientModeSupport.attach(this)
        ambientController.setAmbientOffloadEnabled(true)

        gifViewer = GifViewer(object : GifViewerFragmentStateListener {
            override fun onFragmentDetach() {
                favoritesGifViewModel.setupGifsFavoritesData()
            }
        })

        exploreGifs.setOnClickListener {

            if (!gifViewer.isVisible || !fragmentGifViewer.isShown) {

                GiphyExplore()
                    .invokeGiphyExplore(this@FavoritesGifView)
            }
        }

        favoritesGifViewModel = favoritesGifViewObserverExtension()
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