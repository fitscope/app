package com.mobulous.viewModels.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.SearchRepo
import com.mobulous.helper.NetworkReponse
import com.mobulous.pojo.library.GetChapterLstRes
import com.mobulous.pojo.search.FilterApplyPostPojo
import com.mobulous.pojo.search.GetFilterParameterRes
import com.mobulous.pojo.search.SearchByQueryResultsRes
import kotlinx.coroutines.launch

class SearchViewModel(private val repo: SearchRepo) : ViewModel() {
    private val _searchResults = MutableLiveData<NetworkReponse<SearchByQueryResultsRes>>()
    val searchResults: LiveData<NetworkReponse<SearchByQueryResultsRes>> get() = _searchResults

    private val _searchFilterResults = MutableLiveData<NetworkReponse<SearchByQueryResultsRes>>()
    val searchFilterResults: LiveData<NetworkReponse<SearchByQueryResultsRes>> get() = _searchFilterResults



    private val _filterParameterLst = MutableLiveData<NetworkReponse<GetFilterParameterRes>>()
    val filterParameterLst: LiveData<NetworkReponse<GetFilterParameterRes>> get() = _filterParameterLst

    private val _chapterLstData = MutableLiveData<NetworkReponse<GetChapterLstRes>>()
    val chapterLstData: LiveData<NetworkReponse<GetChapterLstRes>> get() = _chapterLstData



    fun getSearchResults(query: String) {
        viewModelScope.launch {
            try {
                _searchResults.value = NetworkReponse.Success(repo.getSearchResults(query).body())
            } catch (e: Exception) {
                _searchResults.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun getFilterSearchResults(filterData:FilterApplyPostPojo) {
        viewModelScope.launch {
            try {
                _searchFilterResults.value = NetworkReponse.Success(repo.getFilterSearchResults(filterData).body())
            } catch (e: Exception) {
                _searchFilterResults.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }



    fun getFilterParameterLst() {
        viewModelScope.launch {
            try {
                _filterParameterLst.value =
                    NetworkReponse.Success(repo.getFilterParameterLst().body())
            } catch (e: Exception) {
                _filterParameterLst.value = NetworkReponse.Error(e.message ?: "")
            }

        }
    }

    fun getChapterLst(userid: String, programID: String? = null, categoryID: String? = null) {
        viewModelScope.launch {
            try {
                _chapterLstData.value = NetworkReponse.Success(
                    repo.getChapterLst(
                        userID = userid,
                        programID = programID,
                        categoryID = categoryID
                    ).body()
                )
            } catch (e: Exception) {
                _chapterLstData.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }


}