/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 6/19/20 2:46 PM
 * Last modified 6/19/20 2:46 PM
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
import net.geeksempire.geeky.gify.GeekyGifyWatchApplication
import net.geeksempire.geeky.gify.GifFavorite.Extension.favoritesGifViewObserverExtension
import net.geeksempire.geeky.gify.GifFavorite.ViewModel.FavoritesGifViewModel
import net.geeksempire.geeky.gify.GifViewer.GifViewer
import net.geeksempire.geeky.gify.GifViewer.Utils.GifViewerFragmentStateListener
import net.geeksempire.geeky.gify.GiphyExplore.GiphyExplore
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.NetworkConnectionListener
import net.geeksempire.geeky.gify.databinding.FavoritesGifListViewBinding
import javax.inject.Inject

class FavoritesGifView : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider {

    private lateinit var ambientController: AmbientModeSupport.AmbientController

    private lateinit var favoritesGifViewModel: FavoritesGifViewModel

    val gifViewer: GifViewer by lazy {
        GifViewer().apply {
            this.fragmentPlaceHolder = favoritesGifListViewBinding.fragmentPlaceHolder
        }
    }

    lateinit var favoritesGifListViewBinding: FavoritesGifListViewBinding

    @Inject
    lateinit var networkConnectionListener: NetworkConnectionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        favoritesGifListViewBinding = FavoritesGifListViewBinding.inflate(layoutInflater)
        setContentView(favoritesGifListViewBinding.root)

        (application as GeekyGifyWatchApplication)
            .dependencyGraph
            .subDependencyGraph()
            .create(this@FavoritesGifView, favoritesGifListViewBinding.mainView)
            .inject(this@FavoritesGifView)

        ambientController = AmbientModeSupport.attach(this)
        ambientController.setAmbientOffloadEnabled(true)

        gifViewer.gifViewerFragmentStateListener = object : GifViewerFragmentStateListener {

            override fun onFragmentDetach() {

                favoritesGifViewModel.setupGifsFavoritesData()
            }
        }

        favoritesGifListViewBinding.exploreGifs.setOnClickListener {

            if (!gifViewer.isVisible || !favoritesGifListViewBinding.fragmentPlaceHolder.isShown) {

                favoritesGifListViewBinding.fragmentPlaceHolder.visibility = View.VISIBLE
                GiphyExplore()
                    .invokeGiphyExplore(this@FavoritesGifView, favoritesGifListViewBinding.fragmentPlaceHolder)
            }
        }

        favoritesGifViewModel = favoritesGifViewObserverExtension()

        val animatable = getDrawable(R.drawable.animated_search_icon) as Animatable
        animatable.start()

        Glide.with(applicationContext)
            .load(animatable as Drawable)
            .into(favoritesGifListViewBinding.exploreGifs)
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