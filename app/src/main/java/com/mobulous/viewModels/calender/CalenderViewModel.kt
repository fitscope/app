package com.mobulous.viewModels.calender

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.calender.CalenderRepo
import com.mobulous.helper.NetworkReponse
import com.mobulous.pojo.ScheduleLstRes
import com.mobulous.pojo.schedule.GetScheduleLstByDateRes
import com.mobulous.pojo.schedule.RemoveFromScheduleRes
import com.mobulous.pojo.schedule.UpComingSchedulesLstRes
import kotlinx.coroutines.launch

class CalenderViewModel(private val repo: CalenderRepo) : ViewModel() {

    private val _getScheduleByDateLst = MutableLiveData<NetworkReponse<GetScheduleLstByDateRes>>()
    val getScheduleByDateLst: LiveData<NetworkReponse<GetScheduleLstByDateRes>> get() = _getScheduleByDateLst

    private val _getScheduleLst = MutableLiveData<NetworkReponse<ScheduleLstRes>>()
    val getScheduleLst: LiveData<NetworkReponse<ScheduleLstRes>> get() = _getScheduleLst

    private val _getUpComingScheduleLstData = MutableLiveData<NetworkReponse<UpComingSchedulesLstRes>>()
    val getUpcomingSchduleLst: LiveData<NetworkReponse<UpComingSchedulesLstRes>> get() = _getUpComingScheduleLstData
//    private val _makeVideoAsReScheduleData =
//        MutableLiveData<NetworkReponse<ChapterCompleteRes>>()
//    val makeVideoAsReScheduleData: LiveData<NetworkReponse<ChapterCompleteRes>> get() = _makeVideoAsReScheduleData


    private val _removeFromScheduleData = MutableLiveData<NetworkReponse<RemoveFromScheduleRes>>()
    val removeFromSchedule: LiveData<NetworkReponse<RemoveFromScheduleRes>> get() = _removeFromScheduleData

    fun removeFromSchedule(scheduleID: String) {
        viewModelScope.launch {
            try {
                _removeFromScheduleData.value =
                    NetworkReponse.Success(repo.removeSchedule(scheduleID).body())
            } catch (e: Exception) {
                _removeFromScheduleData.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun getUpComingScheduleLst(type: String) {
        viewModelScope.launch {
            try {
                _getUpComingScheduleLstData.value =
                    NetworkReponse.Success(repo.getUpComingScheduleLst(type).body())
            } catch (e: Exception) {
                _getUpComingScheduleLstData.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }



    fun getScheduleByDateLst(type: String, date: String) {
        viewModelScope.launch {
            try {
                _getScheduleByDateLst.value =
                    NetworkReponse.Success(repo.getScheduleLstByDate(type, date).body())
            } catch (e: Exception) {
                _getScheduleByDateLst.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun getScheduleLst() {
        viewModelScope.launch {
            try {
                _getScheduleLst.value = NetworkReponse.Success(repo.getScheduleLst().body())
            } catch (e: Exception) {
                _getScheduleLst.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

//    fun makeChapterAsReSchedule(
//        @Field("userName") userName: String,
//        @Field("chapterId") chapterId: String,
//        @Field("authorizationToken") authorizationToken: String,
//        @Field("date") date: String,
//        @Field("time") time: String,
//        @Field("timeInMint") timeInMint: String,
//        @Field("type") type: String
//    ) {
//        viewModelScope.launch {
//            try {
//                _makeVideoAsReScheduleData.value = NetworkReponse.Success(
//                    repo.makeChapterAsReSchedule(
//                        userName,
//                        chapterId,
//                        authorizationToken,
//                        date,
//                        time,
//                        timeInMint,
//                        type
//                    ).body()
//                )
//            } catch (e: Exception) {
//                _makeVideoAsReScheduleData.value = NetworkReponse.Error(e.message ?: "")
//            }
//        }
//
//    }


}