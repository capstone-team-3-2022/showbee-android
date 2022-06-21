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
import java.text.DecimalFormat
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

        if (sbn.packageName == "com.kakaobank.channel") {
            val current = LocalDateTime.now()
            val formatter = DateTimeFormatter.ISO_DATE
            val formatted = current.format(formatter)
            val financial = Financial()

            if (text != null) {
                val textList = text.split(" ")
                financial.bank = "카카오뱅크"
                financial.content = textList[5]
                financial.date = formatted
                financial.category = "식비"

                if (subText != null) {
                    financial.memo = null
                }
                else {
                    financial.memo = subText.toString()
                }

                if (text.contains("출금")) {
                    financial.inoutcome = false
                }
                else if (text.contains("입금")) {
                    financial.inoutcome = true
                }

//                val decimal = DecimalFormat("####")
                val price = textList[4].replace(",", "")
                    .replace("원", "")
                financial.price = price
//                Log.d(TAG, "1: ${textList[0]}, 2: ${textList[1]}, 3: ${textList[2]}, 4: ${textList[3]}, 5: ${textList[4]}")
//                Log.d(TAG, "onNotificationPosted: $price")
            }

            val financial2 = Financial(date = formatted, content = text.toString(), category = title,
                price = "3000", bank = sbn.packageName, memo = subText.toString(), inoutcome = false)

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