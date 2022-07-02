package com.mobulous.Repo.schedule

import com.mobulous.pojo.ScheduleLstRes
import com.mobulous.pojo.schedule.RescheduleChapterRes
import com.mobulous.pojo.video.ChapterCompleteRes
import com.mobulous.webservices.ApiInterface
import retrofit2.Response
import retrofit2.http.Field

class ScheduleRepo(private val mInterface: ApiInterface) {
    suspend fun getScheduleLst(): Response<ScheduleLstRes> {
        return mInterface.getScheduleLst()
    }

    suspend fun rescheduleChapter(hashMap: HashMap<String, String>): Response<RescheduleChapterRes> =
        mInterface.reScheduleChapter(hashMap)

    suspend fun makeChapterAsCompleteOrSchedule(
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