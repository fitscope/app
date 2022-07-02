package com.mobulous.webservices

import com.mobulous.helper.Constants
import com.mobulous.pojo.CommonChapterIDPojo
import com.mobulous.pojo.ScheduleLstRes
import com.mobulous.pojo.comment.GetCommentLstRes
import com.mobulous.pojo.comment.addcomment.AddCommentRes
import com.mobulous.pojo.comment.reply.CommentReplyRes
import com.mobulous.pojo.dashboard.ProgramPostPojo
import com.mobulous.pojo.dashboard.SeniorPostPjo
import com.mobulous.pojo.dashboard.classes.ClassBike.ClassBikeRes
import com.mobulous.pojo.dashboard.classes.ClassHome.ClassHomeRes
import com.mobulous.pojo.dashboard.classes.ClassRowerRes
import com.mobulous.pojo.dashboard.classes.Ellipticals.ClassEllipticalRes
import com.mobulous.pojo.dashboard.classes.home.HomeCategoryDetailByIdResItem
import com.mobulous.pojo.dashboard.classes.home.HomeParentCategoriesNameResItem
import com.mobulous.pojo.dashboard.classes.onTheFloor.ClassOnTheFloorRes
import com.mobulous.pojo.dashboard.classes.treadmil.ClassTreadmilRes
import com.mobulous.pojo.dashboard.program.*
import com.mobulous.pojo.fav.AddToFavChapterRes
import com.mobulous.pojo.fav.GetFavoriteLstRes
import com.mobulous.pojo.fav.GetFavoriteRes
import com.mobulous.pojo.fav.removeFav.FavChapterRemoveRes
import com.mobulous.pojo.library.*
import com.mobulous.pojo.login.LoginRes
import com.mobulous.pojo.login.loginPost
import com.mobulous.pojo.notification.NotificationRes
import com.mobulous.pojo.saveChapter.SavedChapterRemoveRes
import com.mobulous.pojo.saveChapter.SavedChapterRes
import com.mobulous.pojo.schedule.GetScheduleLstByDateRes
import com.mobulous.pojo.schedule.RemoveFromScheduleRes
import com.mobulous.pojo.schedule.RescheduleChapterRes
import com.mobulous.pojo.schedule.UpComingSchedulesLstRes
import com.mobulous.pojo.search.FilterApplyPostPojo
import com.mobulous.pojo.search.GetFilterParameterRes
import com.mobulous.pojo.search.SearchByQueryResultsRes
import com.mobulous.pojo.signUp.SignUpRes
import com.mobulous.pojo.signUp.SignupPost
import com.mobulous.pojo.video.ChapterCompleteRes
import com.mobulous.pojo.video.VideoDetailPost
import com.mobulous.pojo.video.VideoDetailRes
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.*


interface ApiInterface {
    @POST("userLogin/")
    suspend fun login(@Body post: loginPost): Response<LoginRes>

    @POST("userSignup")
    suspend fun signup(@Body post: SignupPost): Response<SignUpRes>

    @GET("categories")
    fun getHomeParentCategories(
        @Query("per_page") perPageCount: Int,
        @Query("page") pageCount: Int
    ): Call<ArrayList<HomeParentCategoriesNameResItem>>

    @GET("categories/{categoryID}/programs")
    fun getCategoryDetailByID(
        @Path("categoryID") categoryID: String,
        @Query("per_page") perPageCount: Int,
        @Query("page") pageCout: Int
    ): Call<ArrayList<HomeCategoryDetailByIdResItem>>

    @POST("common/getProgramType")
    fun getProgramEachTab(@Body postPojo: ProgramPostPojo): Call<WeightLossRes>

    @POST("common/getProgramTypeById")
    fun getSeniorWorkOutByID(@Body postPojo: SeniorPostPjo): Call<SeniorWorkOutByIDRes>

//    @GET("search?&per_page=25&page=1")
//    fun getSearchQueryResults(@Query("query") query: String): Call<ArrayList<SearchByQueryResultsResItem>>

    @POST("addToFavChapter")
    suspend fun makeChapterFav(
        @Header("userid") userId: String,
        @Body post: CommonChapterIDPojo
    ): Response<AddToFavChapterRes>

    @POST("removeChapterFromFav")
    suspend fun removeChapterFromFav(
        @Header(Constants.userId) userId: String,
        @Body post: CommonChapterIDPojo
    ): Response<FavChapterRemoveRes>

    @POST("addToSaveChapter")
    suspend fun addChapterToSave(
        @Header(Constants.userId) userId: String,
        @Body post: CommonChapterIDPojo
    ): Response<SavedChapterRes>

    @POST("removeChapterFromSave")
    suspend fun removeChapterFromSaved(
        @Header(Constants.userId) userId: String,
        @Body post: CommonChapterIDPojo
    ): Response<SavedChapterRemoveRes>

    @POST("common/getChapterDetails")
    fun getVideoDetailById(@Body post: VideoDetailPost): Call<AddToFavChapterRes>

//    @DELETE("users/chapters/favorite/{chapterID}")
//    fun removeChapterFromFav(@Path("chapterID") id: String): Call<FavChapRemoveRes>


    @FormUrlEncoded
    @POST("common/getFavChapterList")
    fun getFavChapters(@Field(Constants.userId) userId: String): Call<GetFavoriteLstRes>

    @FormUrlEncoded
    @POST("getCommentList")
    suspend fun getComments(
        @FieldMap map: HashMap<String, String>
    ): Response<GetCommentLstRes>

    @FormUrlEncoded
    @POST("addCommments")
    suspend fun addComment(
        @Header(Constants.userId) userId: String,
        @FieldMap map: HashMap<String, String>
    ): Response<AddCommentRes>

    @FormUrlEncoded
    @POST("addCommmentReply")
    suspend fun addCommentReply(
        @Header(Constants.userId) userId: String,
        @FieldMap map: HashMap<String, String>
    ): Response<CommentReplyRes>

//    @FormUrlEncoded
//    @POST("common/getSaveChapterList")
//    fun getSaveList(@Field(Constants.userId) userID: String): Call<SaveChapterLstRes>
//

    @FormUrlEncoded
    @POST("addToScheduleCompleteChapter")
    suspend fun makeChapterAsCompleteOrSchedule(
        @Field("userName") userName: String,
        @Field("chapterId") chapterId: String,
        @Field("authorizationToken") authorizationToken: String,
        @Field("date") date: String,
        @Field("time") time: String,
        @Field("timeInMint") timeInMint: String,
        @Field("type") type: String,
        @Header(Constants.userId) userId: String = Constants.savedUserID
    ): Response<ChapterCompleteRes>

    @POST("getScheduleList")
    suspend fun getScheduleLst(@Header(Constants.userId) userId: String = Constants.savedUserID): Response<ScheduleLstRes>

    @FormUrlEncoded
    @POST("getScheduleUpcomingHistoryList")
    suspend fun getParticularScheduleLst(
        @Field("type") type: String,
        @Field("date") date: String,
        @Header(Constants.userId) userId: String = Constants.savedUserID
    ): Response<GetScheduleLstByDateRes>


    @FormUrlEncoded
    @POST("getScheduleUpcomingHistoryList")
    suspend fun getUpComingScheduleLst(
        @Field("type") type: String,
        @Header(Constants.userId) userId: String = Constants.savedUserID
    ): Response<UpComingSchedulesLstRes>


    /*classes section*/
    @GET("getClassHome/")
    suspend fun getClassHomeData(@Header(Constants.userId) userId: String = Constants.savedUserID): Response<ClassHomeRes>

    @GET("getClassBike/")
    suspend fun getClassBikeData(@Header(Constants.userId) userId: String = Constants.savedUserID): Response<ClassBikeRes>

    @GET("getClassEllipticals/")
    suspend fun getClassEllipticalData(@Header(Constants.userId) userId: String = Constants.savedUserID): Response<ClassEllipticalRes>

    @GET("getClassRower/")
    suspend fun getClassRowerData(@Header(Constants.userId) userId: String = Constants.savedUserID): Response<ClassRowerRes>

    @GET("getClassTreadmill/")
    suspend fun getClassTreadmillData(@Header(Constants.userId) userId: String = Constants.savedUserID): Response<ClassTreadmilRes>

    @GET("getClassOnTheFloor/")
    suspend fun getClassOnTheFloorData(@Header(Constants.userId) userId: String = Constants.savedUserID): Response<ClassOnTheFloorRes>

    /*program section*/
    @GET("getWaitLoss/")
    suspend fun getWeightLoss(@Header(Constants.userId) userId: String = Constants.savedUserID): Response<WeightLossRes>

    @GET("getPrenatalProgram/")
    suspend fun getPrenatalProgram(@Header(Constants.userId) userId: String = Constants.savedUserID): Response<PrenatalRes>

    @GET("getSeniorProgram/")
    suspend fun getSeniorProgram(@Header(Constants.userId) userId: String = Constants.savedUserID): Response<SeniorWorkoutRes>

    @GET("getBootcampsProgram/")
    suspend fun getBootcampsProgram(@Header(Constants.userId) userId: String = Constants.savedUserID): Response<BootCampRes>

    /*library section*/
    @GET("getFavList/")
    suspend fun getFavList(
        @Header(Constants.userId) userId: String
    ): Response<GetFavoriteRes>

    @GET("getSaveList/")
    suspend fun getSaveList(
        @Header(Constants.userId) userId: String
    ): Response<GetSaveLstRes>


    @FormUrlEncoded
    @POST("getChapterList")
    suspend fun getChapterLst(
        @Header(Constants.userId) userId: String,
        @Field(Constants.programId) programID: String? = null,
        @Field(Constants.categoryId) categoryID: String? = null
    ): Response<GetChapterLstRes>

    @FormUrlEncoded
    @POST("removeProgramFromFav")
    suspend fun removeProgramFromFav(
        @Header(Constants.userId) userId: String,
        @Field(Constants.programId) programID: String? = null,
        @Field(Constants.categoryId) categoryId: String? = null
    ): Response<RemoveProgramFromFavRes>

    @FormUrlEncoded
    @POST("removeChapterFromFav")
    suspend fun removeChapterFromFav(
        @Header(Constants.userId) userId: String,
        @Field("chapterId") chapterId: String
    ): Response<RemoveProgramFromFavRes>

    @FormUrlEncoded
    @POST("removeChapterFromSave")
    suspend fun removeChapterFromSave(
        @Header(Constants.userId) userId: String,
        @Field("chapterId") chapterId: String
    ): Response<ChapterRemoveFromSaveRes>

    @FormUrlEncoded
    @POST("removeProgramsFromSave")
    suspend fun removeProgramsFromSave(
        @Header(Constants.userId) userId: String,
        @Field(Constants.programId) programId: String?,
        @Field(Constants.categoryId) categoryId: String?
    ): Response<RemoveProgramFromFavRes>

    @FormUrlEncoded
    @POST("addToSaveProgram")
    suspend fun addToSaveProgram(
        @Header(Constants.userId) userId: String,
        @Field(Constants.programId) programId: String?,
        @Field(Constants.categoryId) categoryID: String?,

        ): Response<AddProgramToSaveRes>

    @FormUrlEncoded
    @POST("addToFavProgram")
    suspend fun addToFavProgram(
        @Header(Constants.userId) userId: String,
        @Field(Constants.programId) programId: String?,
        @Field(Constants.categoryId) categoryID: String?,

        ): Response<AddProgramToFavRes>

    /*Search*/

    @FormUrlEncoded
    @POST("searchList")
    suspend fun getSearchQueryResults(@Field("searchKey") query: String): Response<SearchByQueryResultsRes>


    @POST("filterList")
    suspend fun getFilterSearchResults(@Body filterBody: FilterApplyPostPojo): Response<SearchByQueryResultsRes>


    @GET("getFileterParameters")
    suspend fun getFilterParameterLst(): Response<GetFilterParameterRes>

    @FormUrlEncoded
    @POST("removeFromSchedule")
    suspend fun removeFromSchedule(
        @Field("scheduleId") scheduleID: String,
        @Header(Constants.userId) userId: String = Constants.savedUserID
    ): Response<RemoveFromScheduleRes>

    @GET("notificationList")
    suspend
    fun getNotifications(): Response<NotificationRes>

    @FormUrlEncoded
    @POST("findVideoDetails/")
    suspend fun findVideoDetails(
        @Header(Constants.userId) userId: String,
        @Field("videoId") videoId: String
    ): Response<VideoDetailRes>


    @FormUrlEncoded
    @POST("updateToScheduleCompleteChapter")
    suspend fun reScheduleChapter(
        @FieldMap map: HashMap<String, String>,
        @Header(Constants.userId) userId: String = Constants.savedUserID
    ):Response<RescheduleChapterRes>

}