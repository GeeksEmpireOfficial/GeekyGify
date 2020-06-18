/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 6/18/20 11:18 AM
 * Last modified 6/18/20 11:16 AM
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
import net.geeksempire.geeky.gify.BrowseGifCategory.Extension.createViewModelObserver
import net.geeksempire.geeky.gify.BrowseGifCategory.ViewModel.BrowseCategoryViewModel
import net.geeksempire.geeky.gify.BuildConfig
import net.geeksempire.geeky.gify.GeekyGifyWatchApplication
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.RemoteConfigFunctions
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.NetworkConnectionListener
import net.geeksempire.geeky.gify.Utils.UI.CreateNotification
import net.geeksempire.geeky.gify.databinding.BrowseGifCategoryViewBinding
import javax.inject.Inject

class BrowseCategoryView : AppCompatActivity(), AmbientModeSupport.AmbientCallbackProvider  {

    private lateinit var ambientController: AmbientModeSupport.AmbientController

    private lateinit var browseGifCategoryView: BrowseCategoryViewModel

    lateinit var browseGifCategoryViewBinding: BrowseGifCategoryViewBinding

    @Inject
    lateinit var networkConnectionListener: NetworkConnectionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        browseGifCategoryViewBinding = BrowseGifCategoryViewBinding.inflate(layoutInflater)
        setContentView(R.layout.browse_gif_category_view)

        (application as GeekyGifyWatchApplication)
            .dependencyGraph
            .subDependencyGraph()
            .create(this@BrowseCategoryView, browseGifCategoryViewBinding.mainView)
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

                CreateNotification(
                    applicationContext
                )
                    .notifyManager(
                        getString(R.string.updateAvailable),
                        firebaseRemoteConfig.getString(remoteConfigFunctions.upcomingChangeLogSummaryConfigKey()),
                        firebaseRemoteConfig.getLong(remoteConfigFunctions.versionCodeRemoteConfigKey()).toInt()
                    )
            }
        }
    }

    override fun onResume() {
        super.onResume()
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


