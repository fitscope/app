package com.mobulous.ViewModelFactory.dashboard.classes

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.viewModels.dashboard.classVMs.ClassRowerViewModel

class ClassRowerVMFactory(private val repo: ClassRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return ClassRowerViewModel(repo) as T
    }
}