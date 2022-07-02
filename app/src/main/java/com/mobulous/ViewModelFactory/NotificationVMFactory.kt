package com.mobulous.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobulous.Repo.NotificationRepo
import com.mobulous.Repo.videoDetail.VideoDetailRepo
import com.mobulous.viewModels.NotificationViewModel
import com.mobulous.viewModels.videodetail.VideoDetailViewModel

class NotificationVMFactory(private val repo: NotificationRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return NotificationViewModel(repo) as T
    }
}