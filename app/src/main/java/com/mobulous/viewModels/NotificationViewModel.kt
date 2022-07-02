package com.mobulous.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.NotificationRepo
import com.mobulous.helper.NetworkReponse
import com.mobulous.pojo.notification.NotificationRes
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class NotificationViewModel(val repo: NotificationRepo) : ViewModel() {

    private val _notificationData = MutableLiveData<NetworkReponse<NotificationRes>>()
    val notificationData: LiveData<NetworkReponse<NotificationRes>> get() = _notificationData


    fun getNotifications() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                _notificationData.postValue(NetworkReponse.Success(repo.getNotifications().body()))
            } catch (e: Exception) {
                _notificationData.postValue(NetworkReponse.Error(e.printStackTrace().toString()))
            }
        }
    }


}