package com.fuatogur.whatsappchatlistener

import android.app.Notification
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.util.Log
import com.fuatogur.whatsappchatlistener.model.Message
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class NotificationListener : NotificationListenerService() {

    private val job = SupervisorJob()
    private val scope = CoroutineScope(Dispatchers.IO + job)

    @Inject
    lateinit var database: AppDatabase

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)

        if ((sbn.notification.flags and Notification.FLAG_GROUP_SUMMARY) != 0) return

        val name = sbn.packageName
        val title = sbn.notification.extras.getCharSequence(Notification.EXTRA_TITLE)
        val text = sbn.notification.extras.getCharSequence(Notification.EXTRA_TEXT)

        Log.d(TAG, "RECEIVED MESSAGE $name $title $text")

        if ((name != "com.whatsapp.w4b" && name != "com.whatsapp") || title == null || text == null) return

        scope.launch {
            Log.d(TAG, "STARTED INSERTING")

            database.messageDao().insertAll(
                Message(
                    from = title.toString(),
                    message = text.toString()
                )
            )

            Log.d(TAG, "INSERTED")
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        job.cancel()
    }

    companion object {
        const val TAG = "NOTIFICATION LISTENER"
    }
}