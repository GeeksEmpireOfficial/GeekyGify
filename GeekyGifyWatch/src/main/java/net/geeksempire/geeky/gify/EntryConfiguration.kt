/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 4:27 PM
 * Last modified 2/6/20 2:27 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import net.geeksempire.geeky.gify.BrowseGifCategory.UI.BrowseCategoryView

class EntryConfiguration : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAmbientEnabled()

        startActivity(Intent(applicationContext, BrowseCategoryView::class.java))

        this@EntryConfiguration.finish()
    }
}
