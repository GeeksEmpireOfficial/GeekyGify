/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/16/20 2:08 PM
 * Last modified 2/16/20 2:07 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class EnteryConfiguration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        startActivity(Intent(applicationContext, ShareDataController::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())

        this@EnteryConfiguration.finish()
    }
}