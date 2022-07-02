package com.mobulous.viewModels.library

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.library.LibraryRepo
import com.mobulous.helper.NetworkReponse
import com.mobulous.pojo.fav.GetFavoriteRes
import com.mobulous.pojo.library.*
import com.mobulous.pojo.saveChapter.SavedChapterRemoveRes
import com.mobulous.pojo.saveChapter.SavedChapterRes
import kotlinx.coroutines.launch

class LibraryViewModel(private val repo: LibraryRepo) : ViewModel() {
    private val _getFavLst = MutableLiveData<NetworkReponse<GetFavoriteRes>>()
    val getFavLst: LiveData<NetworkReponse<GetFavoriteRes>> get() = _getFavLst

    private val _getFavChapterLst = MutableLiveData<NetworkReponse<GetChapterLstRes>>()
    val getFavChapterLst: LiveData<NetworkReponse<GetChapterLstRes>> get() = _getFavChapterLst


    private val _removeProgramFromFav = MutableLiveData<NetworkReponse<RemoveProgramFromFavRes>>()
    val removeProgramFromFav: LiveData<NetworkReponse<RemoveProgramFromFavRes>> get() = _removeProgramFromFav

    private val _removeChapterFromFav = MutableLiveData<NetworkReponse<RemoveProgramFromFavRes>>()
    val removeChapterFromFav: LiveData<NetworkReponse<RemoveProgramFromFavRes>> get() = _removeChapterFromFav

    private val _removeProgramFromSave = MutableLiveData<NetworkReponse<RemoveProgramFromFavRes>>()
    val removeProgramFromSave: LiveData<NetworkReponse<RemoveProgramFromFavRes>> get() = _removeProgramFromSave

    private val _removeChapterFromSave = MutableLiveData<NetworkReponse<ChapterRemoveFromSaveRes>>()
    val removeChapterFromSave: LiveData<NetworkReponse<ChapterRemoveFromSaveRes>> get() = _removeChapterFromSave

    private val _addChapterToSave = MutableLiveData<NetworkReponse<SavedChapterRes>>()
    val addChapterToSave: LiveData<NetworkReponse<SavedChapterRes>> get() = _addChapterToSave

    private val _addProgramToSave = MutableLiveData<NetworkReponse<AddProgramToSaveRes>>()
    val addProgramToSave :LiveData<NetworkReponse<AddProgramToSaveRes>> get() = _addProgramToSave

    private val _addProgramToFav = MutableLiveData<NetworkReponse<AddProgramToFavRes>>()
    val addProgramToFave :LiveData<NetworkReponse<AddProgramToFavRes>> get() = _addProgramToFav


    private val _getsaveLst = MutableLiveData<NetworkReponse<GetSaveLstRes>>()
    val getsaveLst: LiveData<NetworkReponse<GetSaveLstRes>> get() = _getsaveLst

    fun getSaveList(userid: String) {
        viewModelScope.launch {
            try {
                _getsaveLst.value = NetworkReponse.Success(repo.getSaveList(userid).body())
            } catch (e: Exception) {
                _getsaveLst.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }



    fun getFavLst(userID: String) {
        viewModelScope.launch {
            try {
                _getFavLst.value = NetworkReponse.Success(repo.getFavLst(userID).body())
            } catch (e: Exception) {
                _getFavLst.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun getFavChapterLst(userID: String, programID: String, categoryID: String) {
        viewModelScope.launch {
            try {
                _getFavChapterLst.value =
                    NetworkReponse.Success(
                        (repo.getFavChapterLst(userID, programID, categoryID).body())
                    )
            } catch (e: Exception) {
                _getFavChapterLst.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun removeProgramFromFav(userID: String, programID: String?,categoryID: String?) {
        viewModelScope.launch {
            try {
                _removeProgramFromFav.value =
                    NetworkReponse.Success(repo.removeProgramFromFav(userID, programID,categoryID).body())
            } catch (e: Exception) {
                _removeProgramFromFav.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun removeChapterFromFav(userID: String, chapterID: String) {
        viewModelScope.launch {
            try {
                _removeChapterFromFav.value =
                    NetworkReponse.Success(repo.removeChapterFromFav(userID, chapterID).body())
            } catch (e: Exception) {
                _removeChapterFromFav.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun removeProgramFromSave(userID: String, programID: String?,categoryID: String?) {
        viewModelScope.launch {
            try {
                _removeProgramFromSave.value =
                    NetworkReponse.Success(repo.removeProgramFromSave(userID, programID,categoryID).body())
            } catch (e: Exception) {
                _removeProgramFromSave.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun removeChapterFromSave(userID: String, chapterID: String) {
        viewModelScope.launch {
            try {
                _removeChapterFromSave.value =
                    NetworkReponse.Success(repo.removeChapterFromSave(userID, chapterID).body())
            } catch (e: Exception) {
                _removeChapterFromSave.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun addChapterToSave(userID: String, chapterID: String) {
        viewModelScope.launch {
            try {
                _addChapterToSave.value =
                    NetworkReponse.Success(repo.addChapterToSave(userID, chapterID).body())
            } catch (e: Exception) {
                _addChapterToSave.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun addChapterToFav(userID: String, chapterID: String) {
        viewModelScope.launch {
            try {
                _addChapterToSave.value =
                    NetworkReponse.Success(repo.addChapterToSave(userID, chapterID).body())
            } catch (e: Exception) {
                _addChapterToSave.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }



    fun addProgramToSave(userID: String, programID: String?,categoryID: String?) {
        viewModelScope.launch {
            try {
                _addProgramToSave.value =
                    NetworkReponse.Success(repo.addProgramToSave(userID, programID,categoryID).body())
            } catch (e: Exception) {
                _addProgramToSave.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun addProgramToFav(userID: String, programID: String?,categoryID: String?) {
        viewModelScope.launch {
            try {
                _addProgramToFav.value =
                    NetworkReponse.Success(repo.addProgramToFav(userID, programID,categoryID).body())
            } catch (e: Exception) {
                _addProgramToFav.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }





}