/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 9:51 AM
 * Last modified 2/6/20 9:33 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.geeksempire.geeky.gify.R

class BrowseGifView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_gif_list_view)
    }
}