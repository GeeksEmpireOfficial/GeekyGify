/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/6/20 11:25 AM
 * Last modified 2/6/20 11:24 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.BrowseGif

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import net.geeksempire.geeky.gify.R

class BrowseGifView : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.browse_gif_list_view)
        Log.d(this.javaClass.simpleName, intent.getStringExtra("CategoryName"))


    }
}