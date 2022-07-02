package com.mobulous.ViewModelFactory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.mobulous.Repo.SearchRepo
import com.mobulous.viewModels.search.SearchViewModel

class SearchVmFactory(private val repo: SearchRepo) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return SearchViewModel(repo) as T
    }
}