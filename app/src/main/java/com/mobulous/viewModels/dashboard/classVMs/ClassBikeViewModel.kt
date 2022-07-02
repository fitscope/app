package com.mobulous.viewModels.dashboard.classVMs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.dashboard.ClassRepo
import com.mobulous.helper.NetworkReponse
import com.mobulous.pojo.dashboard.classes.ClassBike.ClassBikeRes
import com.mobulous.pojo.library.AddProgramToFavRes
import com.mobulous.pojo.library.AddProgramToSaveRes
import com.mobulous.pojo.library.RemoveProgramFromFavRes
import kotlinx.coroutines.launch

class ClassBikeViewModel(private val repo: ClassRepo) : ViewModel() {
    private val _bikedata = MutableLiveData<ClassBikeRes>()
    val bikedata: LiveData<ClassBikeRes> get() = _bikedata

    private val _addProgramToFav = MutableLiveData<NetworkReponse<AddProgramToFavRes>>()
    val addProgramToFav: LiveData<NetworkReponse<AddProgramToFavRes>> get() = _addProgramToFav

    private val _removeProgramFromFav = MutableLiveData<NetworkReponse<RemoveProgramFromFavRes>>()
    val removeProgramFromFav: LiveData<NetworkReponse<RemoveProgramFromFavRes>> get() = _removeProgramFromFav

    private val _addProgramToSave = MutableLiveData<NetworkReponse<AddProgramToSaveRes>>()
    val addProgramToSave: LiveData<NetworkReponse<AddProgramToSaveRes>> get() = _addProgramToSave

    private val _removeProgramFromSave = MutableLiveData<NetworkReponse<RemoveProgramFromFavRes>>()
    val removeProgramFromSave: LiveData<NetworkReponse<RemoveProgramFromFavRes>> get() = _removeProgramFromSave

    fun getClassBikeData() {
        viewModelScope.launch {
            _bikedata.postValue(repo.getClassBikeData().body())
        }
    }


    fun addProgramTofav(userid: String, programID: String?, categoryId: String?) {
        viewModelScope.launch {
            try {
                _addProgramToFav.value = NetworkReponse.Success(
                    repo.addProgramTofav(userid, programID, categoryId).body()
                )
            } catch (e: Exception) {
                _addProgramToFav.value = NetworkReponse.Error(
                    e.message ?: ""
                )
            }

        }
    }

    fun removeProgramFromFav(userID: String, programID: String?, categoryID: String?) {
        viewModelScope.launch {
            try {
                _removeProgramFromFav.value =
                    NetworkReponse.Success(
                        repo.removeProgramFromFav(userID, programID, categoryID).body()
                    )
            } catch (e: Exception) {
                _removeProgramFromFav.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun addProgramToSave(userID: String, programID: String?, categoryID: String?) {
        viewModelScope.launch {
            try {
                _addProgramToSave.value =
                    NetworkReponse.Success(
                        repo.addProgramToSave(userID, programID, categoryID).body()
                    )
            } catch (e: Exception) {
                _addProgramToSave.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun removeProgramFromSave(userID: String, programID: String?, categoryID: String?) {
        viewModelScope.launch {
            try {
                _removeProgramFromSave.value =
                    NetworkReponse.Success(
                        repo.removeProgramFromSave(userID, programID, categoryID).body()
                    )
            } catch (e: Exception) {
                _removeProgramFromSave.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }
}