/*
 * Copyright © 2020 By Geeks Empire.
 *
 * Created by Elias Fazel on 2/18/20 12:57 PM
 * Last modified 2/18/20 12:44 PM
 *
 * Licensed Under MIT License.
 * https://opensource.org/licenses/MIT
 */

package net.geeksempire.geeky.gify.Utils.Notification

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.graphics.drawable.IconCompat
import net.geeksempire.geeky.gify.R

class CreateNotification (var context: Context) {

    fun notifyManager(titleText: String, contentText: String, notificationId: Int) {
        val notificationManager: NotificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val notificationBuilder: NotificationCompat.Builder = NotificationCompat.Builder(context, notificationId.toString())

        notificationBuilder.setContentTitle(titleText)
        notificationBuilder.setContentText(contentText)
        notificationBuilder.setTicker(context.getResources().getString(R.string.app_name))
        notificationBuilder.setSmallIcon(R.drawable.icon_geeky_gify)
        notificationBuilder.setAutoCancel(true)
        notificationBuilder.color = context.getColor(R.color.default_color)
        notificationBuilder.priority = NotificationCompat.PRIORITY_DEFAULT

        val newUpdate = Intent(Intent.ACTION_VIEW, Uri.parse(context.getString(R.string.playStoreLink) + context.packageName))
        val newUpdatePendingIntent = PendingIntent.getActivity(context, 5, newUpdate, PendingIntent.FLAG_UPDATE_CURRENT)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannel = NotificationChannel(
                context.packageName,
                context.getString(R.string.app_name),
                NotificationManager.IMPORTANCE_HIGH
            )
            notificationManager.createNotificationChannel(notificationChannel)
            notificationBuilder.setChannelId(context.getPackageName())
        }


        val builderActionNotification: NotificationCompat.Action.Builder =
            NotificationCompat.Action.Builder(
                IconCompat.createWithResource(context, R.drawable.icon_geeky_gify),
                context.getString(R.string.rateReview),
                newUpdatePendingIntent
            )
        notificationBuilder.addAction(builderActionNotification.build())

        notificationBuilder.setContentIntent(newUpdatePendingIntent)
        notificationManager.notify(notificationId,
            notificationBuilder.build())
    }
}