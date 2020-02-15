/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/14/20 4:26 PM
 * Last modified 2/14/20 4:24 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify

import android.app.Application
import net.geeksempire.geeky.gify.Utils.DependencyInjection.DaggerDependencyGraph
import net.geeksempire.geeky.gify.Utils.DependencyInjection.DependencyGraph

class GeekyGifyWatchApplication : Application() {

    val dependencyGraph: DependencyGraph by lazy {
        DaggerDependencyGraph.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
    }
}