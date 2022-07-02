package com.mobulous.pojo.dashboard.program

import com.google.gson.annotations.SerializedName
import com.mobulous.pojo.dashboard.ProgramChaptersDataItem

data class BootCampRes(

    @field:SerializedName("data")
    val data: ArrayList<BootCampDataItem?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)


data class BootCampDataItem(

    @field:SerializedName("programTitle")
    val programTitle: String? = null,

    @field:SerializedName("chapters")
    val chapters: ArrayList<ProgramChaptersDataItem?>? = null,

    @field:SerializedName("categoryTitle")
    val categoryTitle: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("sort")
    val sort: Int? = null,

    @field:SerializedName("isFav")
    val isFav: Boolean? = null,

    @field:SerializedName("categoryId")
    val categoryId: Int? = null,

    @field:SerializedName("programId")
    val programId: Int? = null,

    @field:SerializedName("isSave")
    val isSave: Boolean? = null
)
