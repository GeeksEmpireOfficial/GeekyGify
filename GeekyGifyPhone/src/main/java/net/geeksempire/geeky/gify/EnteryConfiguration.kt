/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/18/20 12:57 PM
 * Last modified 2/18/20 12:51 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.geeksempire.geeky.gify.Utils.ServerConnections.RemoteConfigFunctions

class EnteryConfiguration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.VERSION_NAME.contains("BETA")) {
            RemoteConfigFunctions(applicationContext).joinedBetaProgram(true)
        }

        startActivity(Intent(applicationContext, ShareDataController::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())

        this@EnteryConfiguration.finish()
    }
}