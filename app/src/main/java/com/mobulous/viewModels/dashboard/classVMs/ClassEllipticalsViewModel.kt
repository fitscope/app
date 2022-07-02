package com.mobulous.viewModels.dashboard.classVMs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.pojo.dashboard.classes.Ellipticals.ClassEllipticalRes
import kotlinx.coroutines.launch

class ClassEllipticalsViewModel(private val repo: ClassRepo) : ViewModel() {
    private val _ellipticalData = MutableLiveData<ClassEllipticalRes>()
    val ellipticalData: LiveData<ClassEllipticalRes> get() = _ellipticalData


    fun getEllipticalsData() {
        viewModelScope.launch {
            if (repo.getEllipticalsData().body() != null) {
                _ellipticalData.value = repo.getEllipticalsData().body()
            }
        }
    }

}