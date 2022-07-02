package com.mobulous.Repo

import com.mobulous.pojo.library.GetChapterLstRes
import com.mobulous.pojo.search.FilterApplyPostPojo
import com.mobulous.pojo.search.GetFilterParameterRes
import com.mobulous.pojo.search.SearchByQueryResultsRes
import com.mobulous.webservices.ApiInterface
import retrofit2.Response

class SearchRepo(private val mInterface: ApiInterface) {
    suspend fun getSearchResults(query: String): Response<SearchByQueryResultsRes> =
        mInterface.getSearchQueryResults(query = query)

    suspend fun getFilterSearchResults(filterData: FilterApplyPostPojo): Response<SearchByQueryResultsRes> =
        mInterface.getFilterSearchResults(filterData)


    suspend fun getFilterParameterLst(): Response<GetFilterParameterRes> =
        mInterface.getFilterParameterLst()

    suspend fun getChapterLst(
        userID: String,
        programID: String? = null,
        categoryID: String? = null
    ): Response<GetChapterLstRes> {
        return mInterface.getChapterLst(userID, programID, categoryID)
    }
}