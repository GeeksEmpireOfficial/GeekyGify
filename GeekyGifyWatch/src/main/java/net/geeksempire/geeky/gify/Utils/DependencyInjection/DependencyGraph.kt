/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 6:03 PM
 * Last modified 2/8/20 5:10 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.DependencyInjection

import android.content.Context
import dagger.BindsInstance
import dagger.Component
import net.geeksempire.geeky.gify.EntryConfiguration
import net.geeksempire.geeky.gify.Utils.DependencyInjection.Modules.SubDependencyGraphs
import net.geeksempire.geeky.gify.Utils.DependencyInjection.Modules.System.SystemCheckpointModule
import net.geeksempire.geeky.gify.Utils.DependencyInjection.SubComponents.NetworkSubDependencyGraph
import javax.inject.Singleton

@Singleton
@Component (modules = [SystemCheckpointModule::class, SubDependencyGraphs::class])
interface DependencyGraph {

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance context: Context): DependencyGraph
    }

    fun subDependencyGraph(): NetworkSubDependencyGraph.Factory

    fun inject(entryConfiguration: EntryConfiguration)
}
