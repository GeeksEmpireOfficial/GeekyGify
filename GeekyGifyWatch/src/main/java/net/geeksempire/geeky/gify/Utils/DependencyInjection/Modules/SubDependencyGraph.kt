/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 6:03 PM
 * Last modified 2/8/20 5:10 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.DependencyInjection.Modules

import dagger.Module
import net.geeksempire.geeky.gify.Utils.DependencyInjection.SubComponents.NetworkSubDependencyGraph

@Module(subcomponents = [NetworkSubDependencyGraph::class])
class SubDependencyGraphs