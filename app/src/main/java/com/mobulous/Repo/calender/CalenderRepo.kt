package com.mobulous.Repo.calender

import com.mobulous.pojo.ScheduleLstRes
import com.mobulous.pojo.schedule.GetScheduleLstByDateRes
import com.mobulous.pojo.schedule.RemoveFromScheduleRes
import com.mobulous.pojo.schedule.UpComingSchedulesLstRes
import com.mobulous.pojo.video.ChapterCompleteRes
import com.mobulous.webservices.ApiInterface
import retrofit2.Response
import retrofit2.http.Field

class CalenderRepo(private val mInterface: ApiInterface) {
    suspend fun getScheduleLstByDate(
        type: String,
        date: String
    ): Response<GetScheduleLstByDateRes> {
        return mInterface.getParticularScheduleLst(type, date)
    }

    suspend fun getScheduleLst(): Response<ScheduleLstRes> {
        return mInterface.getScheduleLst()
    }

    suspend fun getUpComingScheduleLst(type: String): Response<UpComingSchedulesLstRes> =
        mInterface.getUpComingScheduleLst(type)


    suspend fun removeSchedule(scheduleID: String): Response<RemoveFromScheduleRes> =
        mInterface.removeFromSchedule(scheduleID)

    suspend fun makeChapterAsReSchedule(
        @Field("userName") userName: String,
        @Field("chapterId") chapterId: String,
        @Field("authorizationToken") authorizationToken: String,
        @Field("date") date: String,
        @Field("time") time: String,
        @Field("timeInMint") timeInMint: String,
        @Field("type") type: String
    ): Response<ChapterCompleteRes> {
        return mInterface.makeChapterAsCompleteOrSchedule(
            userName,
            chapterId,
            authorizationToken,
            date,
            time,
            timeInMint,
            type
        )
    }

}