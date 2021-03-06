/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 12/6/20 7:56 AM
 * Last modified 12/6/20 7:54 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.SystemCheckpoint

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.Network
import android.os.Handler
import android.os.Looper
import android.provider.Settings
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.offline_indicator.view.*
import net.geeksempire.geeky.gify.R

class NetworkConnectionListener (private var appCompatActivity: AppCompatActivity,
                                 var rootView: ConstraintLayout,
                                 var systemCheckpoint: SystemCheckpoint) :  ConnectivityManager.NetworkCallback() {

    val connectivityManager = appCompatActivity.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    var offlineIndicator: View

    init {
        connectivityManager.registerDefaultNetworkCallback(this@NetworkConnectionListener)

        offlineIndicator = LayoutInflater.from(appCompatActivity).inflate(R.layout.offline_indicator, rootView, false)
    }

    override fun onAvailable(network: Network) {
        super.onAvailable(network)

        appCompatActivity.runOnUiThread {

            Handler((Looper.getMainLooper()).postDelayed({
                if (systemCheckpoint.networkConnection()) {
                    Log.d(this@NetworkConnectionListener.javaClass.simpleName, "Network Available")

                    rootView.removeView(offlineIndicator)

                    appCompatActivity.window.statusBarColor = appCompatActivity.getColor(R.color.default_color_game_dark)
                    appCompatActivity.window.navigationBarColor = appCompatActivity.getColor(R.color.default_color_game_dark)
                }
            }, 555)
        }
    }

    override fun onLost(network: Network) {
        super.onLost(network)

        appCompatActivity.runOnUiThread {

            Handler((Looper.getMainLooper()).postDelayed({
                if (!systemCheckpoint.networkConnection()) {
                    Log.d(this@NetworkConnectionListener.javaClass.simpleName, "Network Lost")

                    rootView.addView(offlineIndicator)

                    appCompatActivity.window.statusBarColor = appCompatActivity.getColor(R.color.cyberGreen)
                    appCompatActivity.window.navigationBarColor = appCompatActivity.getColor(R.color.cyberGreen)

                    Glide.with(appCompatActivity)
                        .asGif()
                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                        .load(R.drawable.no_internet_connection)
                        .into(offlineIndicator.offlineWait)

                    offlineIndicator.offlineWait.setOnClickListener {
                        appCompatActivity.startActivity(Intent(Settings.ACTION_SETTINGS).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))

                        appCompatActivity.finish()
                    }
                }
            }, 555)
        }
    }

    fun unregisterDefaultNetworkCallback() {
        connectivityManager.unregisterNetworkCallback(this@NetworkConnectionListener)
    }
}