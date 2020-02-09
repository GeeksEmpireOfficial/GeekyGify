/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/8/20 6:03 PM
 * Last modified 2/8/20 6:03 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.DependencyInjection.SubComponents

import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import dagger.BindsInstance
import dagger.Subcomponent
import net.geeksempire.geeky.gify.BrowseGif.UI.BrowseGifView
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.BrowseCategoryView
import net.geeksempire.geeky.gify.Utils.DependencyInjection.Modules.System.Network.NetworkConnectionModule
import net.geeksempire.geeky.gify.Utils.DependencyInjection.Scopes.ActivityScope

@ActivityScope
@Subcomponent(modules = [NetworkConnectionModule::class])
interface NetworkSubDependencyGraph {

    @Subcomponent.Factory
    interface Factory {
        fun create(@BindsInstance appCompatActivity: AppCompatActivity, @BindsInstance constraintLayout: ConstraintLayout): NetworkSubDependencyGraph
    }

    fun inject(browseCategoryView: BrowseCategoryView)
    fun inject(browseGifView: BrowseGifView)
}