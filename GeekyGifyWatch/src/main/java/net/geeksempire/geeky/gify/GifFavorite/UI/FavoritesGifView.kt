/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 4/28/20 4:28 AM
 * Last modified 4/28/20 4:28 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.UI

import android.graphics.drawable.Animatable
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.wear.ambient.AmbientModeSupport
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.favorites_gif_list_view.*
import net.geeksempire.geeky.gify.GeekyGifyWatchApplication
import net.geeksempire.geeky.gify.GifFavorite.Extension.favoritesGifViewObserverExtension
import net.geeksempire.geeky.gify.GifFavorite.ViewModel.FavoritesGifViewModel
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.GifViewer.Utils.GifViewerFragmentStateListener
import net.geeksempire.geeky.gify.GiphyExplore.GiphyExplore
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.NetworkConnectionListener
import javax.inject.Inject

class FavoritesGifView : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider {

    private lateinit var ambientController: AmbientModeSupport.AmbientController

    private lateinit var favoritesGifViewModel: FavoritesGifViewModel

    var gifViewer: GifViewer = GifViewer()

    @Inject
    lateinit var networkConnectionListener: NetworkConnectionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.favorites_gif_list_view)

        (application as GeekyGifyWatchApplication)
            .dependencyGraph
            .subDependencyGraph()
            .create(this@FavoritesGifView, mainView)
            .inject(this@FavoritesGifView)

        ambientController = AmbientModeSupport.attach(this)
        ambientController.setAmbientOffloadEnabled(true)

        gifViewer = GifViewer()
        gifViewer.gifViewerFragmentStateListener = object : GifViewerFragmentStateListener {

            override fun onFragmentDetach() {

                favoritesGifViewModel.setupGifsFavoritesData()
            }
        }

        exploreGifs.setOnClickListener {

            if (!gifViewer.isVisible || !fragmentPlaceHolder.isShown) {

                fragmentPlaceHolder.visibility = View.VISIBLE
                GiphyExplore()
                    .invokeGiphyExplore(this@FavoritesGifView)
            }
        }

        favoritesGifViewModel = favoritesGifViewObserverExtension()

        val animatable = getDrawable(R.drawable.animated_search_icon) as Animatable
        animatable.start()

        Glide.with(applicationContext)
            .load(animatable as Drawable)
            .into(exploreGifs)
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