package com.mobulous.viewModels.dashboard.classVMs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.pojo.dashboard.classes.ClassRowerRes
import kotlinx.coroutines.launch

class ClassRowerViewModel(private val repo: ClassRepo) : ViewModel() {
    private val _rowerData = MutableLiveData<ClassRowerRes>()
    val rowerData: LiveData<ClassRowerRes> get() = _rowerData


    fun getRowersData() {
        viewModelScope.launch {
            if (repo.getRowerData().body() != null) {
                _rowerData.value = repo.getRowerData().body()
            }
        }
    }

}