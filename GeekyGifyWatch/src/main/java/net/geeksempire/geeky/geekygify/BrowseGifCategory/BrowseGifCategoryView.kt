package net.geeksempire.geeky.geekygify.BrowseGifCategory

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class BrowseGifCategoryView : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAmbientEnabled()

    }
}