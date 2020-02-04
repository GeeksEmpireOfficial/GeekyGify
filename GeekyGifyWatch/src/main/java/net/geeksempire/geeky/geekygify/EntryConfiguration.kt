package net.geeksempire.geeky.geekygify

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class EntryConfiguration : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setAmbientEnabled()
    }
}
