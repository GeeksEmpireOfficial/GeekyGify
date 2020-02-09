/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 6:03 PM
 * Last modified 2/8/20 6:03 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.SystemCheckpoint

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import javax.inject.Inject

interface InterfaceSystemCheckpoint {

}

class SystemCheckpoint @Inject constructor(var context: Context) : InterfaceSystemCheckpoint {

    fun networkConnection(): Boolean {
        var networkAvailable = false
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork
        val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork)
        if (networkCapabilities != null) {
            if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                networkAvailable = true
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                networkAvailable = true
            } else if (networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) {
                networkAvailable = true
            }
        }
        return networkAvailable
    }
}