/*
 * Copyright © 2020 By Geeks Empire. 
 *
 * Created by Elias Fazel on 2/18/20 5:56 PM
 * Last modified 2/18/20 5:56 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.DataController

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.android.synthetic.main.received_data_controller.*
import net.geeksempire.geeky.gify.BuildConfig
import net.geeksempire.geeky.gify.DataController.Parameter.DataParameter
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.Notification.CreateNotification
import net.geeksempire.geeky.gify.Utils.ServerConnections.RemoteConfigFunctions
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.NetworkConnectionListener
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.SystemCheckpoint
import net.geeksempire.geeky.gify.Utils.UI.SnackbarView


class ShareDataController : AppCompatActivity() {

    val receivedDataController: Fragment = ReceivedDataController()
    val nullDataController: Fragment = NullDataController()

    private lateinit var networkConnectionListener: NetworkConnectionListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.shared_data_controller)

        val vmBuilder = VmPolicy.Builder()
        StrictMode.setVmPolicy(vmBuilder.build())

        val systemCheckpoint = SystemCheckpoint(applicationContext)

        val snackbarView = SnackbarView()

        networkConnectionListener = NetworkConnectionListener(
            this@ShareDataController,
            mainView,
            systemCheckpoint)


        if (intent.data == null) {

            snackbarView.snackBarViewFail(this@ShareDataController,
                mainView,
                getString(R.string.errorOccurred))

            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, 0)
                .replace(R.id.fragmentPlaceHolder, nullDataController, "Received Data Controller")
                .commit()

        } else {

            val linkToGif = intent.data!!.getQueryParameter(Intent.EXTRA_STREAM).toString()
            val additionalText = intent.data!!.getQueryParameter(Intent.EXTRA_TEXT).toString()

            receivedDataController.arguments = Bundle().apply {
                putString(DataParameter.LINK_TO_GIF, linkToGif)
                putString(DataParameter.ADDITIONAL_TEXT, additionalText)
            }

            supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(android.R.anim.fade_in, 0)
                .replace(R.id.fragmentPlaceHolder, receivedDataController, "Received Data Controller")
                .commit()
        }
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

        this@ShareDataController.finish()
        networkConnectionListener.unRegisterDefaultNetworkCallback()
    }
}
