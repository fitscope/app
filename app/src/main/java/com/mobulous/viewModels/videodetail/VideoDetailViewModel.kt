package com.mobulous.viewModels.videodetail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobulous.Repo.videoDetail.VideoDetailRepo
import com.mobulous.helper.Constants
import com.mobulous.helper.NetworkReponse
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
import kotlinx.coroutines.launch
import retrofit2.http.Field

class VideoDetailViewModel(private val repo: VideoDetailRepo) : ViewModel() {
    private val _videoDetailData = MutableLiveData<VideoDetailRes>()
    val videoDetailData: LiveData<VideoDetailRes> get() = _videoDetailData

    private val _makeVideoAsComOrScheduleData =
        MutableLiveData<NetworkReponse<ChapterCompleteRes>>()
    val makeVideoAsComOrScheduleData: LiveData<NetworkReponse<ChapterCompleteRes>> get() = _makeVideoAsComOrScheduleData

    private val _addChapterToFavData = MutableLiveData<NetworkReponse<AddToFavChapterRes>>()
    val addChapterToFavData: LiveData<NetworkReponse<AddToFavChapterRes>> get() = _addChapterToFavData

    private val _removeChapterToFavData = MutableLiveData<NetworkReponse<FavChapterRemoveRes>>()
    val removeChapterToFavData: LiveData<NetworkReponse<FavChapterRemoveRes>> get() = _removeChapterToFavData

    private val _addChapterToSave = MutableLiveData<NetworkReponse<SavedChapterRes>>()
    val addChapterToSave: LiveData<NetworkReponse<SavedChapterRes>> get() = _addChapterToSave

    private val _removeChapterFromSave = MutableLiveData<NetworkReponse<SavedChapterRemoveRes>>()
    val removeChapterFromSave: LiveData<NetworkReponse<SavedChapterRemoveRes>> get() = _removeChapterFromSave

    private val _getCommentLstData = MutableLiveData<NetworkReponse<GetCommentLstRes>>()
    val getCommentLstData: LiveData<NetworkReponse<GetCommentLstRes>> get() = _getCommentLstData

    private val _addCommentData = MutableLiveData<NetworkReponse<AddCommentRes>>()
    val addCommentData: LiveData<NetworkReponse<AddCommentRes>> get() = _addCommentData

    private val _addCommentReplyData = MutableLiveData<NetworkReponse<CommentReplyRes>>()
    val addCommentReplyData: LiveData<NetworkReponse<CommentReplyRes>> get() = _addCommentReplyData


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

    fun getVideoDetail(videoId: String, userID: String) {
        viewModelScope.launch {
            _videoDetailData.value = repo.getVideoDetail(videoId, userID = userID).body()
        }
    }

    fun addChatperToFav(userID: String, model: CommonChapterIDPojo) {
        viewModelScope.launch {
            try {
                _addChapterToFavData.value =
                    NetworkReponse.Success(repo.addChapterToFav(userID, model).body())
            } catch (e: Exception) {
                _addChapterToFavData.value = NetworkReponse.Error(e.message.toString())
            }

        }
    }

    fun removeChatperToFav(userID: String, model: CommonChapterIDPojo) {
        viewModelScope.launch {
            try {
                _removeChapterToFavData.value =
                    NetworkReponse.Success(repo.removeChapterToFav(userID, model).body())
            } catch (e: Exception) {
                _removeChapterToFavData.value = NetworkReponse.Error(e.message.toString())
            }

        }
    }

    fun addChatperToSave(userID: String, model: CommonChapterIDPojo) {
        viewModelScope.launch {
            try {
                _addChapterToSave.value =
                    NetworkReponse.Success(repo.addChapterToSave(userID, model).body())
            } catch (e: Exception) {
                _addChapterToSave.value = NetworkReponse.Error(e.message.toString())
            }

        }
    }

    fun removeChapterFromSave(userID: String, model: CommonChapterIDPojo) {
        viewModelScope.launch {
            try {
                _removeChapterFromSave.value =
                    NetworkReponse.Success(repo.removeChapterFromSave(userID, model).body())
            } catch (e: Exception) {
                _removeChapterFromSave.value = NetworkReponse.Error(e.message.toString())
            }
        }
    }

    fun getComments(fieldMap: HashMap<String, String>) {
        viewModelScope.launch {
            try {
                _getCommentLstData.value = NetworkReponse.Success(repo.getComments(fieldMap).body())
            } catch (e: Exception) {
                _getCommentLstData.value = NetworkReponse.Error(e.message.toString())
            }
        }
    }

    fun addComment(userID: String, fieldMap: HashMap<String, String>) {
        viewModelScope.launch {
            try {
                _addCommentData.value =
                    NetworkReponse.Success(repo.addComment(userID, fieldMap).body())
            } catch (e: Exception) {
                _addCommentData.value = NetworkReponse.Error(e.message.toString())
            }
        }
    }

    fun addCommentReply(userID: String, fieldMap: HashMap<String, String>) {
        viewModelScope.launch {
            try {
                _addCommentReplyData.value =
                    NetworkReponse.Success(repo.addCommentReply(userID, fieldMap).body())
            } catch (e: Exception) {
                _addCommentReplyData.value = NetworkReponse.Error(e.message.toString())
            }
        }
    }

}