package com.mobulous.Repo.videoDetail

import com.mobulous.helper.Constants
import com.mobulous.pojo.CommonChapterIDPojo
import com.mobulous.pojo.comment.GetCommentLstRes
import com.mobulous.pojo.comment.addcomment.AddCommentRes
import com.mobulous.pojo.comment.reply.CommentReplyRes
import com.mobulous.pojo.fav.AddToFavChapterRes
import com.mobulous.pojo.fav.removeFav.FavChapterRemoveRes
import com.mobulous.pojo.saveChapter.SavedChapterRemoveRes
import com.mobulous.pojo.saveChapter.SavedChapterRes
import com.mobulous.pojo.video.ChapterCompleteRes
import com.mobulous.pojo.video.VideoDetailRes
import com.mobulous.webservices.ApiInterface
import retrofit2.Response
import retrofit2.http.Field
import retrofit2.http.Header

class VideoDetailRepo(private val mInterface: ApiInterface) {

    suspend fun addChapterToFav(
        userID: String,
        model: CommonChapterIDPojo
    ): Response<AddToFavChapterRes> {
        return mInterface.makeChapterFav(userID, model)
    }

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

    suspend fun removeChapterToFav(
        userID: String,
        model: CommonChapterIDPojo
    ): Response<FavChapterRemoveRes> {
        return mInterface.removeChapterFromFav(userID, model)
    }

    suspend fun getVideoDetail(videoId: String, userID: String): Response<VideoDetailRes> {
        return mInterface.findVideoDetails(userId = userID, videoId)
    }

    suspend fun addChapterToSave(
        userID: String,
        model: CommonChapterIDPojo
    ): Response<SavedChapterRes> {
        return mInterface.addChapterToSave(userID, model)
    }

    suspend fun removeChapterFromSave(
        userID: String,
        model: CommonChapterIDPojo
    ): Response<SavedChapterRemoveRes> {
        return mInterface.removeChapterFromSaved(userID, model)
    }

    suspend fun getComments(fieldMap: HashMap<String, String>): Response<GetCommentLstRes> {
        return mInterface.getComments(fieldMap)
    }

    suspend fun addComment(
        userID: String,
        fieldMap: HashMap<String, String>
    ): Response<AddCommentRes> {
        return mInterface.addComment(userID, fieldMap)
    }

    suspend fun addCommentReply(
        userID: String,
        fieldMap: HashMap<String, String>
    ): Response<CommentReplyRes> {
        return mInterface.addCommentReply(userID, fieldMap)
    }


}