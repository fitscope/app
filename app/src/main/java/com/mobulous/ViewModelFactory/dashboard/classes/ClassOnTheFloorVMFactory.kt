package com.mobulous.ViewModelFactory.dashboard.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.viewModels.dashboard.classVMs.ClassOnTheFloorViewModel

class ClassOnTheFloorVMFactory(private val repo: ClassRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ClassOnTheFloorViewModel(repo) as T
    }
}