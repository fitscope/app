package com.mobulous.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobulous.Repo.videoDetail.VideoDetailRepo
import com.mobulous.viewModels.videodetail.VideoDetailViewModel

class VideoDetailVMFactory(private val repo: VideoDetailRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return VideoDetailViewModel(repo) as T
    }
}