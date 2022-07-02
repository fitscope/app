package com.mobulous.pojo.dashboard.program

import com.google.gson.annotations.SerializedName
import com.mobulous.pojo.dashboard.ProgramChaptersDataItem

data class WeightLossRes(

    @field:SerializedName("data")
    val data: ArrayList<WeightLossDataItem?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class WeightLossDataItem(

    @field:SerializedName("chapters")
    val chapters: ArrayList<ProgramChaptersDataItem?>? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("isFav")
    val isFav: Boolean? = null,

    @field:SerializedName("isSave")
    val isSave: Boolean? = null,

    @field:SerializedName("categoryId")
    val categoryId: String? = null,

    @field:SerializedName("programId")
    val programId: String? = null


)


