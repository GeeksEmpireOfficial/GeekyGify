/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 6:03 PM
 * Last modified 2/8/20 2:49 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.DependencyInjection.Modules.System

import dagger.Binds
import dagger.Module
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.InterfaceSystemCheckpoint
import net.geeksempire.geeky.gify.Utils.SystemCheckpoint.SystemCheckpoint

@Module
abstract class SystemCheckpointModule {

    @Binds
    abstract fun provideSystemCheckpoint(systemCheckpoint: SystemCheckpoint/*This is Instance Of Return Type*/): InterfaceSystemCheckpoint
}