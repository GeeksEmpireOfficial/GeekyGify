/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 6:03 PM
 * Last modified 2/8/20 6:03 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.DependencyInjection.Modules.System.Network

import android.net.ConnectivityManager
import dagger.Binds
import dagger.Module
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.NetworkConnectionListener

@Module
abstract class NetworkConnectionModule {

    @Binds
    abstract fun provideNetworkConnectionListener(networkConnectionListener: NetworkConnectionListener/*This is Instance Of Return Type*/): ConnectivityManager.NetworkCallback
}