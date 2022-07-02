package com.mobulous.ViewModelFactory.dashboard

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobulous.Repo.calender.CalenderRepo
import com.mobulous.viewModels.calender.CalenderViewModel

class CalenderVMFactory(private val repo: CalenderRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return CalenderViewModel(repo) as T
    }
}