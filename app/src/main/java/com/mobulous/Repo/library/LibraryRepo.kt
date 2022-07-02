package com.mobulous.Repo.library

import com.mobulous.pojo.CommonChapterIDPojo
import com.mobulous.pojo.fav.AddToFavChapterRes
import com.mobulous.pojo.fav.GetFavoriteRes
import com.mobulous.pojo.library.*
import com.mobulous.pojo.saveChapter.SavedChapterRes
import com.mobulous.webservices.ApiInterface
import retrofit2.Response

class LibraryRepo(private val mInterface: ApiInterface) {
    suspend fun getFavLst(userID: String): Response<GetFavoriteRes> {
        return mInterface.getFavList(userID)
    }

    suspend fun getSaveList(userID: String): Response<GetSaveLstRes> {
        return mInterface.getSaveList(userID)
    }


    suspend fun getFavChapterLst(
        userID: String,
        programID: String,
        categoryID: String
    ): Response<GetChapterLstRes> {
        return mInterface.getChapterLst(userID, programID, categoryID)
    }

    suspend fun removeProgramFromFav(
        userID: String,
        programID: String?, categoryID: String?
    ): Response<RemoveProgramFromFavRes> {
        return mInterface.removeProgramFromFav(userID, programID, categoryID)
    }

    suspend fun removeChapterFromFav(
        userID: String,
        chapterID: String
    ): Response<RemoveProgramFromFavRes> {
        return mInterface.removeChapterFromFav(userID, chapterID)
    }

    suspend fun removeProgramFromSave(
        userID: String,
        programID: String?,
        categoryID: String?
    ): Response<RemoveProgramFromFavRes> {
        return mInterface.removeProgramsFromSave(userID, programID, categoryID)
    }

    suspend fun removeChapterFromSave(
        userID: String,
        chapterID: String
    ): Response<ChapterRemoveFromSaveRes> {
        return mInterface.removeChapterFromSave(userID, chapterID)
    }

    suspend fun addChapterToSave(
        userID: String,
        chapterID: String
    ): Response<SavedChapterRes> {
        return mInterface.addChapterToSave(userID, CommonChapterIDPojo(chapterID))
    }
    suspend fun addChapterToFav(
        userID: String,
        chapterID: String
    ): Response<AddToFavChapterRes> {
        return mInterface.makeChapterFav(userID, CommonChapterIDPojo(chapterID))
    }

    suspend fun addProgramToSave(
        userID: String,
        programID: String?,
        categoryID: String?
    ): Response<AddProgramToSaveRes> {
        return mInterface.addToSaveProgram(userID, programID, categoryID)
    }
     suspend fun addProgramToFav(
        userID: String,
        programID: String?,
        categoryID: String?
    ): Response<AddProgramToFavRes> {
        return mInterface.addToFavProgram(userID, programID, categoryID)
    }




}