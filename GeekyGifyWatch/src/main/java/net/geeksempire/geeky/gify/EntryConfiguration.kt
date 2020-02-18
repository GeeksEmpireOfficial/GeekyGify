/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/18/20 12:57 PM
 * Last modified 2/18/20 12:42 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify

import android.content.Intent
import android.os.Bundle
import android.provider.Settings
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.wear.ambient.AmbientModeSupport
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.entry_configuration.*
import kotlinx.android.synthetic.main.offline_indicator.view.*
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.BrowseCategoryView
import net.geeksempire.geeky.gify.Utils.ServerConnections.RemoteConfigFunctions
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.SystemCheckpoint
import javax.inject.Inject

class EntryConfiguration : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider {

    private lateinit var ambientController: AmbientModeSupport.AmbientController

    @Inject
    lateinit var systemCheckpoint: SystemCheckpoint

    override fun onCreate(savedInstanceState: Bundle?) {
        val dependencyGraph = (application as GeekyGifyWatchApplication)
            .dependencyGraph
        dependencyGraph.inject(this@EntryConfiguration)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.entry_configuration)

        ambientController = AmbientModeSupport.attach(this)
        ambientController.setAmbientOffloadEnabled(true)

        if (systemCheckpoint.networkConnection()) {
            startActivity(Intent(applicationContext, BrowseCategoryView::class.java))

            this@EntryConfiguration.finish()
        } else {
            val offlineIndicator = LayoutInflater.from(this@EntryConfiguration).inflate(R.layout.offline_indicator, mainView, false)
            mainView.addView(offlineIndicator)

            Glide.with(this@EntryConfiguration)
                .asGif()
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .load(R.drawable.no_internet_connection)
                .into(offlineIndicator.offlineWait)

            offlineIndicator.offlineWait.setOnClickListener {
                startActivity(Intent(Settings.ACTION_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                this@EntryConfiguration.finish()
            }
        }
    }

    override fun onStart() {
        super.onStart()

        if (BuildConfig.VERSION_NAME.contains("BETA")) {
            RemoteConfigFunctions(applicationContext).joinedBetaProgram(true)
        }
    }

    override fun onPause() {
        super.onPause()
    }

    override fun getAmbientCallback(): AmbientModeSupport.AmbientCallback {

        return AmbientCallbackEntryConfiguration()
    }

    private class AmbientCallbackEntryConfiguration : AmbientModeSupport.AmbientCallback() {

        override fun onEnterAmbient(ambientDetails: Bundle?) {

        }

        override fun onExitAmbient() {

        }

        override fun onUpdateAmbient() {

        }
    }
}
