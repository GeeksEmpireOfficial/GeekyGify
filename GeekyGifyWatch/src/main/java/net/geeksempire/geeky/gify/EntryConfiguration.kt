/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 3:48 PM
 * Last modified 2/4/20 3:47 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import net.geeksempire.geeky.gify.BrowseGifCategory.BrowseCategoryView

class EntryConfiguration : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAmbientEnabled()


        startActivity(Intent(applicationContext, BrowseCategoryView::class.java))

        this@EntryConfiguration.finish()
    }
}
