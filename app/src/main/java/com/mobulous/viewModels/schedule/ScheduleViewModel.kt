package com.mobulous.viewModels.schedule

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.schedule.ScheduleRepo
import com.mobulous.helper.NetworkReponse
import com.mobulous.pojo.ScheduleLstRes
import com.mobulous.pojo.schedule.RescheduleChapterRes
import com.mobulous.pojo.video.ChapterCompleteRes
import kotlinx.coroutines.launch
import retrofit2.http.Field

class ScheduleViewModel(private val repo: ScheduleRepo) : ViewModel() {

    private val _getScheduleLst = MutableLiveData<NetworkReponse<ScheduleLstRes>>()
    val getScheduleLst: LiveData<NetworkReponse<ScheduleLstRes>> get() = _getScheduleLst

    private val _rescheduleData = MutableLiveData<NetworkReponse<RescheduleChapterRes>>()
    val rescheduleData: LiveData<NetworkReponse<RescheduleChapterRes>> get() = _rescheduleData


    private val _makeVideoAsComOrScheduleData =
        MutableLiveData<NetworkReponse<ChapterCompleteRes>>()
    val makeVideoAsComOrScheduleData: LiveData<NetworkReponse<ChapterCompleteRes>> get() = _makeVideoAsComOrScheduleData


    fun getScheduleLst() {
        viewModelScope.launch {
            try {
                _getScheduleLst.value = NetworkReponse.Success(repo.getScheduleLst().body())
            } catch (e: Exception) {
                _getScheduleLst.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }

    fun rescheduleChapter(hashMap: HashMap<String, String>) {
        viewModelScope.launch {
            try {
                _rescheduleData.value =
                    NetworkReponse.Success(repo.rescheduleChapter(hashMap).body())
            } catch (e: Exception) {
                _rescheduleData.value = NetworkReponse.Error(e.message ?: "")
            }
        }
    }


    fun makeChapterAsCompleteOrSchedule(
        @Field("userName") userName: String,
        @Field("chapterId") chapterId: String,
        @Field("authorizationToken") authorizationToken: String,
        @Field("date") date: String,
        @Field("time") time: String,
        @Field("timeInMint") timeInMint: String,
        @Field("type") type: String
    ) {
        viewModelScope.launch {
            try {
                _makeVideoAsComOrScheduleData.value = NetworkReponse.Success(
                    repo.makeChapterAsCompleteOrSchedule(
                        userName,
                        chapterId,
                        authorizationToken,
                        date,
                        time,
                        timeInMint,
                        type
                    ).body()
                )
            } catch (e: Exception) {
                _makeVideoAsComOrScheduleData.value = NetworkReponse.Error(e.message ?: "")
            }
        }

    }


}