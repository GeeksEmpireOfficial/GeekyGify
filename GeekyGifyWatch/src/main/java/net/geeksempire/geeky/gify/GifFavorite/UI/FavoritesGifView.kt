/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/12/20 6:10 PM
 * Last modified 2/12/20 6:07 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.GifFavorite.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.wear.ambient.AmbientModeSupport
import kotlinx.android.synthetic.main.favorites_gif_list_view.*
import net.geeksempire.geeky.gify.GeekyGifyApplication
import net.geeksempire.geeky.gify.R

class FavoritesGifView : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider {

    private lateinit var ambientController: AmbientModeSupport.AmbientController

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