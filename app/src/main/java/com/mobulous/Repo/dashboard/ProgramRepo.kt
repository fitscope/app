package com.mobulous.Repo.dashboard

import com.mobulous.pojo.dashboard.program.BootCampRes
import com.mobulous.pojo.dashboard.program.PrenatalRes
import com.mobulous.pojo.dashboard.program.SeniorWorkoutRes
import com.mobulous.pojo.dashboard.program.WeightLossRes
import com.mobulous.pojo.library.AddProgramToFavRes
import com.mobulous.pojo.library.AddProgramToSaveRes
import com.mobulous.pojo.library.RemoveProgramFromFavRes
import com.mobulous.webservices.ApiInterface
import retrofit2.Response

class ProgramRepo(private val mInterface: ApiInterface) {

    suspend fun getWeightLoss(): Response<WeightLossRes> {
        return mInterface.getWeightLoss()
    }

    suspend fun getPrenatalData(): Response<PrenatalRes> {
        return mInterface.getPrenatalProgram()
    }

    suspend fun getSeniorWorkOutData(): Response<SeniorWorkoutRes> {
        return mInterface.getSeniorProgram()
    }

    suspend fun getBootCampData(): Response<BootCampRes> {
        return mInterface.getBootcampsProgram()
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