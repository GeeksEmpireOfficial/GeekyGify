/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/20/20 12:35 PM
 * Last modified 2/20/20 12:34 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Networking.Notification

import android.util.Log
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

class CloudMessages : FirebaseMessagingService() {

    override fun onMessageReceived(remoteMessage: RemoteMessage) {

        Log.d(this@CloudMessages.javaClass.simpleName, "${remoteMessage.from}")

        remoteMessage.data.isNotEmpty().let {
            Log.d(this@CloudMessages.javaClass.simpleName, "${remoteMessage.data}")
        }

        remoteMessage.notification?.let {
            Log.d(this@CloudMessages.javaClass.simpleName, "${it.body}")
        }
    }
}