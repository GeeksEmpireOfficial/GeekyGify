/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/18/20 12:57 PM
 * Last modified 2/18/20 12:45 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGifCategory.UI

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.wear.ambient.AmbientModeSupport
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.android.synthetic.main.browse_gif_category_view.*
import net.geeksempire.geeky.gify.BrowseGifCategory.Extension.createViewModelObserver
import net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel.BrowseCategoryViewModel
import net.geeksempire.geeky.gify.BuildConfig
import net.geeksempire.geeky.gify.GeekyGifyWatchApplication
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.Notification.CreateNotification
import net.geeksempire.geeky.gify.Utils.ServerConnections.RemoteConfigFunctions
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

    override fun onStart() {
        super.onStart()

        val configSettings = FirebaseRemoteConfigSettings.Builder()
            .setMinimumFetchIntervalInSeconds(13*60)
            .build()

        val firebaseRemoteConfig = FirebaseRemoteConfig.getInstance().apply {
            this.setConfigSettingsAsync(configSettings)
        }

        firebaseRemoteConfig.setDefaultsAsync(R.xml.remote_config_default)
        firebaseRemoteConfig.fetchAndActivate().addOnSuccessListener {

            val remoteConfigFunctions = RemoteConfigFunctions(applicationContext)

            if (firebaseRemoteConfig.getLong(remoteConfigFunctions.versionCodeRemoteConfigKey()) > BuildConfig.VERSION_CODE) {

                CreateNotification(applicationContext)
                    .notifyManager(
                        getString(R.string.updateAvailable),
                        firebaseRemoteConfig.getString(remoteConfigFunctions.upcomingChangeLogSummaryConfigKey()),
                        firebaseRemoteConfig.getLong(remoteConfigFunctions.versionCodeRemoteConfigKey()).toInt()
                    )
            }
        }
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


