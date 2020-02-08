/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/7/20 3:48 PM
 * Last modified 2/7/20 3:17 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.SystemCheckpoint

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities

class SystemCheckpoint {

    fun networkConnection(context: Context): Boolean {
        var networkAvailable = false
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val activeNetwork = connectivityManager.activeNetwork
            val networkCapabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork)
            if (networkCapabilities != null) {
                if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    networkAvailable = true
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    networkAvailable = true
                } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                    networkAvailable = true
                }
            }
        }
        return networkAvailable
    }
}