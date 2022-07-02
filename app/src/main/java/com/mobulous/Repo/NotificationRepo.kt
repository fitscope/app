package com.mobulous.Repo

import com.mobulous.pojo.notification.NotificationRes
import com.mobulous.webservices.ApiInterface
import retrofit2.Response

class NotificationRepo(private val mInterface: ApiInterface) {

    suspend fun getNotifications(): Response<NotificationRes> = mInterface.getNotifications()
}