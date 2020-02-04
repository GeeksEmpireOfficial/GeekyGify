/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/4/20 6:52 AM
 * Last modified 2/4/20 6:52 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.geekygify

import android.os.Bundle
import android.support.wearable.activity.WearableActivity

class EntryConfiguration : WearableActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setAmbientEnabled()

    }
}
