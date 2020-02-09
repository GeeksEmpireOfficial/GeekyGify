/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 6:03 PM
 * Last modified 2/8/20 4:08 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify

import android.app.Application
import net.geeksempire.geeky.gify.Utils.DependencyInjection.DaggerDependencyGraph
import net.geeksempire.geeky.gify.Utils.DependencyInjection.DependencyGraph

class GeekyGifyApplication : Application() {

    val dependencyGraph: DependencyGraph by lazy {
        DaggerDependencyGraph.factory().create(applicationContext)
    }

    override fun onCreate() {
        super.onCreate()
    }
}