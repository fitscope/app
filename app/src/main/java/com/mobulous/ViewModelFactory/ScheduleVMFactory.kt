package com.mobulous.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobulous.Repo.schedule.ScheduleRepo
import com.mobulous.Repo.videoDetail.VideoDetailRepo
import com.mobulous.viewModels.schedule.ScheduleViewModel
import com.mobulous.viewModels.videodetail.VideoDetailViewModel

class ScheduleVMFactory(private val repo: ScheduleRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ScheduleViewModel(repo) as T
    }
}