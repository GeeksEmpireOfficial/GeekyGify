/*
 * Copyright © 2020 By Geeks Empire. 
 *
 * Created by Elias Fazel on 2/11/20 12:26 PM
 * Last modified 2/11/20 12:26 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class ShareDataController : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        println(">>> " + intent.data?.getQueryParameter(Intent.EXTRA_STREAM))
        println(">>> " + intent.data?.getQueryParameter(Intent.EXTRA_TEXT))

        Toast.makeText(applicationContext,
            "${intent.data?.getQueryParameter(Intent.EXTRA_STREAM)} \n\n\n ${intent.data?.getQueryParameter(Intent.EXTRA_TEXT)}",
            Toast.LENGTH_LONG).show()
    }
}
