package com.team3.showbee

import android.app.Notification
import android.app.Service
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.ViewModelProvider
import com.team3.showbee.data.entity.Event
import com.team3.showbee.data.entity.Financial
import com.team3.showbee.data.network.NetworkResponse
import com.team3.showbee.data.repository.financial.FinancialRepository
import com.team3.showbee.di.ApiModule
import com.team3.showbee.ui.viewmodel.FinancialViewModel
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*

class NotificationListener : NotificationListenerService() {
    override fun onNotificationRemoved(sbn: StatusBarNotification) {
        super.onNotificationRemoved(sbn)
//        Log.d(
//            TAG, "onNotificationRemoved ~ " +
//                    " packageName: " + sbn.packageName +
//                    " id: " + sbn.id
//        )
    }

    override fun onNotificationPosted(sbn: StatusBarNotification) {
        super.onNotificationPosted(sbn)
        val notification = sbn.notification
        val extras = sbn.notification.extras
        val title = extras.getString(Notification.EXTRA_TITLE)
        val text = extras.getCharSequence(Notification.EXTRA_TEXT)
        val subText = extras.getCharSequence(Notification.EXTRA_SUB_TEXT)
        val smallIcon = notification.smallIcon
        val largeIcon = notification.getLargeIcon()

        if (sbn.packageName == "com.samsung.android.messaging"
            || sbn.packageName == "viva.republica.toss") {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ISO_DATE
            val formatted = current.format(formatter)

            val financial = Financial(date = formatted, content = text.toString(), category = title,
                price = "3000", bank = sbn.packageName, memo = subText.toString(), inoutcome = true)

            val httpClient = ApiModule.provideOkHttpClient()
            val retrofit = ApiModule.provideRetrofit(httpClient)
            val service = ApiModule.provideApiService(retrofit)

            GlobalScope.launch {
                val response = service.createFinancialResponse(financial)

                when(response) {
                    is NetworkResponse.Success -> {
                        Log.d(TAG, "onNotificationPosted: success")
                    }
                    is NetworkResponse.ApiError -> {
                        Log.d(TAG, "onNotificationPosted: api error")
                    }
                    is NetworkResponse.NetworkError -> {
                        Log.d(TAG, "onNotificationPosted: network error")
                    }
                    is NetworkResponse.UnknownError -> {
                        Log.d(TAG, "onNotificationPosted: unknown error")
                    }
                }
            }
        }
        else {
            Log.d(
                TAG, "onNotificationPosted ~~~ " +
                        " packageName: " + sbn.packageName +
                        " id: " + sbn.id +
                        " postTime: " + sbn.postTime +
                        " title: " + title +
                        " text : " + text +
                        " subText: " + subText
            )
        }
    }

    companion object {
        const val TAG = "MyNotificationListener"
    }
}