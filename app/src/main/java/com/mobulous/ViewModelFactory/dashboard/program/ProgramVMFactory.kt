package com.mobulous.ViewModelFactory.dashboard.program

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobulous.Repo.dashboard.ProgramRepo
import com.mobulous.viewModels.dashboard.programVMs.ProgramViewModel

class ProgramVMFactory(private val repo: ProgramRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ProgramViewModel(repo) as T
    }
}