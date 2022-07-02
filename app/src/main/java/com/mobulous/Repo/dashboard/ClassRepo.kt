package com.mobulous.Repo.dashboard

import com.mobulous.pojo.dashboard.classes.ClassBike.ClassBikeRes
import com.mobulous.pojo.dashboard.classes.ClassHome.ClassHomeRes
import com.mobulous.pojo.dashboard.classes.ClassRowerRes
import com.mobulous.pojo.dashboard.classes.Ellipticals.ClassEllipticalRes
import com.mobulous.pojo.dashboard.classes.onTheFloor.ClassOnTheFloorRes
import com.mobulous.pojo.dashboard.classes.treadmil.ClassTreadmilRes
import com.mobulous.pojo.library.AddProgramToFavRes
import com.mobulous.pojo.library.AddProgramToSaveRes
import com.mobulous.pojo.library.RemoveProgramFromFavRes
import com.mobulous.webservices.ApiInterface
import retrofit2.Response

class ClassRepo(private val mInterface: ApiInterface) {
    suspend fun getClassBikeData(): Response<ClassBikeRes> {
        return mInterface.getClassBikeData()
    }

    suspend fun getClassHomeData(): Response<ClassHomeRes> {
        return mInterface.getClassHomeData()
    }

    suspend fun getEllipticalsData(): Response<ClassEllipticalRes> {
        return mInterface.getClassEllipticalData()
    }

    suspend fun getRowerData(): Response<ClassRowerRes> {
        return mInterface.getClassRowerData()
    }

    suspend fun getTreadMilData(): Response<ClassTreadmilRes> {
        return mInterface.getClassTreadmillData()
    }

    suspend fun getOnTheFloorData(): Response<ClassOnTheFloorRes> {
        return mInterface.getClassOnTheFloorData()
    }

    suspend fun addProgramTofav(
        userid: String,
        programID: String?,
        categoryID: String?
    ): Response<AddProgramToFavRes> {
        return mInterface.addToFavProgram(userid, programID, categoryID)
    }

    suspend fun removeProgramFromFav(
        userID: String,
        programID: String?,categoryID: String?
    ): Response<RemoveProgramFromFavRes> {
        return mInterface.removeProgramFromFav(userID, programID,categoryID)
    }

    suspend fun addProgramToSave(
        userID: String,
        programID: String?,
        categoryID: String?
    ): Response<AddProgramToSaveRes> {
        return mInterface.addToSaveProgram(userID, programID,categoryID)
    }

    suspend fun removeProgramFromSave(
        userID: String,
        programID: String?,
        categoryID: String?
    ): Response<RemoveProgramFromFavRes> {
        return mInterface.removeProgramsFromSave(userID, programID,categoryID)
    }

}