package com.mobulous.viewModels.dashboard.programVMs

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.dashboard.ProgramRepo
import com.mobulous.helper.NetworkReponse
import com.mobulous.pojo.dashboard.program.BootCampRes
import com.mobulous.pojo.dashboard.program.PrenatalRes
import com.mobulous.pojo.dashboard.program.SeniorWorkoutRes
import com.mobulous.pojo.dashboard.program.WeightLossRes
import com.mobulous.pojo.library.AddProgramToFavRes
import com.mobulous.pojo.library.AddProgramToSaveRes
import com.mobulous.pojo.library.RemoveProgramFromFavRes
import kotlinx.coroutines.launch

class ProgramViewModel(private val repo: ProgramRepo) : ViewModel() {
    private val _weightLossData = MutableLiveData<NetworkReponse<WeightLossRes>>()
    val weightLossData: LiveData<NetworkReponse<WeightLossRes>> get() = _weightLossData

    private val _prenatalData = MutableLiveData<NetworkReponse<PrenatalRes>>()
    val prenatalData: LiveData<NetworkReponse<PrenatalRes>> get() = _prenatalData

    private val _seniorWorkOutData = MutableLiveData<NetworkReponse<SeniorWorkoutRes>>()
    val seniorWorkOutData: LiveData<NetworkReponse<SeniorWorkoutRes>> get() = _seniorWorkOutData

    private val _bootCampDataa = MutableLiveData<NetworkReponse<BootCampRes>>()
    val bootcampData: LiveData<NetworkReponse<BootCampRes>> get() = _bootCampDataa

    private val _addProgramToFav = MutableLiveData<NetworkReponse<AddProgramToFavRes>>()
    val addProgramToFav: LiveData<NetworkReponse<AddProgramToFavRes>> get() = _addProgramToFav

    private val _removeProgramFromFav = MutableLiveData<NetworkReponse<RemoveProgramFromFavRes>>()
    val removeProgramFromFav: LiveData<NetworkReponse<RemoveProgramFromFavRes>> get() = _removeProgramFromFav

    private val _addProgramToSave = MutableLiveData<NetworkReponse<AddProgramToSaveRes>>()
    val addProgramToSave: LiveData<NetworkReponse<AddProgramToSaveRes>> get() = _addProgramToSave

    private val _removeProgramFromSave = MutableLiveData<NetworkReponse<RemoveProgramFromFavRes>>()
    val removeProgramFromSave: LiveData<NetworkReponse<RemoveProgramFromFavRes>> get() = _removeProgramFromSave


    fun getWeightLoss() {
        viewModelScope.launch {
            try {
                _weightLossData.value = NetworkReponse.Success(repo.getWeightLoss().body())
            } catch (e: Exception) {
                _weightLossData.value = NetworkReponse.Error(e.message ?: "")
            }

        }
    }

    fun getPrenatalData() {
        viewModelScope.launch {
            try {
                _prenatalData.value = NetworkReponse.Success(repo.getPrenatalData().body())
            } catch (e: Exception) {
                _prenatalData.value = NetworkReponse.Error(e.message ?: "")
            }

        }
    }

    fun getSeniorWorkOutData() {
        viewModelScope.launch {
            try {
                _seniorWorkOutData.value =
                    NetworkReponse.Success(repo.getSeniorWorkOutData().body())
            } catch (e: Exception) {
                _seniorWorkOutData.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun getBootCampData() {
        viewModelScope.launch {
            try {
                _bootCampDataa.value = NetworkReponse.Success(repo.getBootCampData().body())
            } catch (e: Exception) {
                _bootCampDataa.value = NetworkReponse.Error(e.message ?: "")
            }
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