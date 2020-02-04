/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 7:42 AM
 * Last modified 2/4/20 7:42 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.geekygify

import android.content.Intent
import android.os.Bundle
import android.support.wearable.activity.WearableActivity
import net.geeksempire.geeky.geekygify.BrowseGifCategory.BrowseGifCategoryView

class EntryConfiguration : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAmbientEnabled()


        startActivity(Intent(applicationContext, BrowseGifCategoryView::class.java))

        this@EntryConfiguration.finish()
    }
}
