package com.mobulous.ViewModelFactory.dashboard.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.viewModels.dashboard.classVMs.ClassTreadMilViewModel

class ClassTreadMilVMFactory(private val repo: ClassRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ClassTreadMilViewModel(repo) as T
    }
}