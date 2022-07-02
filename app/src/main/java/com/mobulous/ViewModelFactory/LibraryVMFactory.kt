package com.mobulous.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobulous.Repo.library.LibraryRepo
import com.mobulous.viewModels.library.LibraryViewModel

class LibraryVMFactory(private val repo: LibraryRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return LibraryViewModel(repo) as T
    }
}