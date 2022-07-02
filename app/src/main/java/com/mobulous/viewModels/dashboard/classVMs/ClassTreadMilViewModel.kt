package com.mobulous.viewModels.dashboard.classVMs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.pojo.dashboard.classes.treadmil.ClassTreadmilRes
import kotlinx.coroutines.launch

class ClassTreadMilViewModel(private val repo: ClassRepo) : ViewModel() {
    private val _treadmilData = MutableLiveData<ClassTreadmilRes>()
    val treadmilData: LiveData<ClassTreadmilRes> get() = _treadmilData


    fun getTreadmilData() {
        viewModelScope.launch {
            if (repo.getTreadMilData().body() != null) {
                _treadmilData.value = repo.getTreadMilData().body()
            }
        }
    }

}