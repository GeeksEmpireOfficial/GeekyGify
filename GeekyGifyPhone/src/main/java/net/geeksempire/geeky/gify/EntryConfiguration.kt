/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 3/2/20 7:28 AM
 * Last modified 3/2/20 7:27 AM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify

import android.app.ActivityOptions
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import net.geeksempire.geeky.gify.SharedDataController.DataController
import net.geeksempire.geeky.gify.Utils.Networking.ServerConnections.RemoteConfigFunctions

class EntryConfiguration : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (BuildConfig.VERSION_NAME.contains("BETA")) {
            RemoteConfigFunctions(applicationContext).joinedBetaProgram(true)
        }

        startActivity(Intent(applicationContext, DataController::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        }, ActivityOptions.makeCustomAnimation(applicationContext, android.R.anim.fade_in, android.R.anim.fade_out).toBundle())

        this@EntryConfiguration.finish()
    }
}