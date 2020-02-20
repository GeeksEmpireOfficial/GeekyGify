/*
 * Copyright © 2020 By Geeks Empire. 
 *
 * Created by Elias Fazel on 2/20/20 2:24 PM
 * Last modified 2/20/20 2:20 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.DataController

import android.content.Intent
import android.graphics.drawable.Drawable
import android.graphics.drawable.Icon
import android.os.Bundle
import android.os.StrictMode
import android.os.StrictMode.VmPolicy
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.RequestOptions
import com.bumptech.glide.request.target.Target
import com.google.firebase.remoteconfig.FirebaseRemoteConfig
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings
import kotlinx.android.synthetic.main.received_data_controller.*
import net.geeksempire.geeky.gify.BuildConfig
import net.geeksempire.geeky.gify.DataController.Parameter.DataParameter
import net.geeksempire.geeky.gify.R
import net.geeksempire.geeky.gify.Utils.Converter.ConvertFile
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.RemoteConfigFunctions
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.NetworkConnectionListener
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.SystemCheckpoint
import net.geeksempire.geeky.gify.Utils.UI.CreateNotification
import net.geeksempire.geeky.gify.Utils.UI.PopupAppShortcuts
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

            createPopupShortcutAd(firebaseRemoteConfig)
        }
    }

    override fun onDestroy() {
        super.onDestroy()

        this@ShareDataController.finish()
        networkConnectionListener.unRegisterDefaultNetworkCallback()
    }

    private fun createPopupShortcutAd(firebaseRemoteConfig: FirebaseRemoteConfig) {

        val shortcutId: String? = firebaseRemoteConfig.getString(getString(R.string.shortcutId))
        val shortcutLabel: String = firebaseRemoteConfig.getString(getString(R.string.shortcutLabel))
        val shortcutIconLink: String? = firebaseRemoteConfig.getString(getString(R.string.shortcutIconLink))
        val shortcutActionLink: String? = firebaseRemoteConfig.getString(getString(R.string.shortcutActionLink))

        shortcutId?.let {

            Glide.with(applicationContext)
                .load(shortcutIconLink)
                .diskCacheStrategy(DiskCacheStrategy.DATA)
                .apply(RequestOptions.circleCropTransform())
                .addListener(object : RequestListener<Drawable> {
                    override fun onLoadFailed(e: GlideException?, model: Any?, target: Target<Drawable>?, isFirstResource: Boolean): Boolean {
                        e?.printStackTrace()

                        return false
                    }

                    override fun onResourceReady(resource: Drawable?, model: Any?, target: Target<Drawable>?, dataSource: DataSource?, isFirstResource: Boolean): Boolean {

                        val popupAppShortcuts: PopupAppShortcuts = PopupAppShortcuts(applicationContext)

                        resource?.let { icon ->

                            shortcutActionLink?.let { actionLink ->

                                popupAppShortcuts.create(
                                    shortcutId,
                                    shortcutLabel,
                                    Icon.createWithBitmap(ConvertFile().drawableToBitmap(icon)),
                                    actionLink
                                )
                            }
                        }

                        return true
                    }
                })
                .submit()
        }
    }
}
