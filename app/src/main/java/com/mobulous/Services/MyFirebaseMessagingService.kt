package com.mobulous.Services

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.ContentResolver
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.mobulous.fitscope.BuildConfig
import com.mobulous.fitscope.R
import com.mobulous.helper.Constants
import com.mobulous.webservices.ApiConstants


class MyFirebaseMessagingService : FirebaseMessagingService() {
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        val msgMap = remoteMessage.data
        //{body=Hi, varu - Abhi has sent you a friend request., type=frndReuest, sound=default, title=New friend request received, value=5ff4125a285e180fe6b8d671}
        Log.w("testNotification", remoteMessage.data.toString())
        sendNotification(
            (if (msgMap["title"] == null) "FitScope" else msgMap["title"])!!,
            msgMap["body"],
            msgMap["type"],
            msgMap["value"],
            msgMap["notificationId"],
            msgMap["notiCount"]
        )
        //  sendNotification( msgMap["title"]!!,msgMap["body"]!!,remoteMessage.data)
    }

    override fun onNewToken(s: String) {
        super.onNewToken(s)
        Log.e("onNewToken:", s)
        ApiConstants.AccessToken = s
    }

    private fun sendNotification(
        title: String,
        messageBody: String?,
        type: String?,
        value: String?,
        notificationId: String?,
        notiCount: String?
    ) {
        val i = Intent(BuildConfig.APPLICATION_ID)
        LocalBroadcastManager.getInstance(this).sendBroadcast(Intent().apply {
            action = Constants.explicitBroadCastAction
        })
        val broadcast = true
//        getSharedPreferences(PREF_NAME, MODE_PRIVATE).edit().putString(PREF_NOTI_COUNT, notiCount).apply()
//        if (type == NOTI_TYPE_FRIEND_REQUEST) {
//
//            intent = Intent(this, FriendRequestActivity::class.java)
//                    .putExtra("invitedBy", value)
//                    .putExtra("notificationid", notificationId)
//
//        }
//
//        var intent: Intent = Intent(this, AcitivityHome::class.java)
//            .putExtra("invitedBy", value)
//            .putExtra("notificationid", notificationId)

//        if (broadcast) {
//            sendBroadcast(i)
//        }
//        val intent: Intent = when (type)
//        {
//            "dashboardAccReq", "dashboardAccRej", "dashboardAccAcc" -> {
//                Intent(this, SwitchUsers::class.java)
//                    .putExtra(Enums.invitedBy.toString(), value)
//                    .putExtra(Enums.notificationid.toString(), notificationId)
//            }
//            "appAccept", "appComplete" -> {
//                Intent(this, Appointment::class.java)
//                    .putExtra(Enums.invitedBy.toString(), value)
//                    .putExtra(Enums.notificationid.toString(), notificationId)
//            }
//            else -> {
//                Intent(this, AcitivityHome::class.java)
//                    .putExtra(Enums.invitedBy.toString(), value)
//                    .putExtra(Enums.notificationid.toString(), notificationId)
//            }
//        }
//
//        intent.putExtra(Enums.typeNoti.toString(), type)
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
//        val pendingIntent = PendingIntent.getActivity(
//            this, 0, intent,
//            PendingIntent.FLAG_UPDATE_CURRENT
//        )

        val channelId = getString(R.string.app_name)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.drawable.app_icon)
            .setContentTitle(title)
            .setContentText(messageBody ?: "No message for you!")
            .setAutoCancel(true)
            .setSound(defaultSoundUri)
            .setDefaults(NotificationCompat.PRIORITY_HIGH)
            .setPriority(NotificationCompat.DEFAULT_ALL)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
        //  .setFullScreenIntent(pendingIntent, false)
        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val sound =
                Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" + packageName + "/" + R.raw.keep) //Here is FILE_NAME is the name of file that you want to play
            val attributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                .build()
            NotificationChannel(
                channelId,
                Constants.NotificationChannelName,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                enableLights(true)
//                enableVibration(true)
//                vibrationPattern = LongArray(0)
                setSound(sound, attributes)
            }.also {
                notificationBuilder.setChannelId(channelId)
                notificationManager.createNotificationChannel(it)
            }

        }
        notificationManager.notify(1, notificationBuilder.build())
    }


}