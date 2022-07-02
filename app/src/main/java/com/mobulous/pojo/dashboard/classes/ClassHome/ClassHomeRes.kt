package com.mobulous.pojo.dashboard.classes.ClassHome

import com.google.gson.annotations.SerializedName
import com.mobulous.pojo.dashboard.CommonChaptersItem

data class ClassHomeRes(

    @field:SerializedName("data")
    val data: ArrayList<ClassHomeDataItems?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class ClassHomeDataItems(

    @field:SerializedName("chapters")
    val chapters: ArrayList<CommonChaptersItem?>? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("categoryId")
    val categoryId: Int? = null,

    @field:SerializedName("categoryTitle")
    val categoryTitle: String? = null,
    @field:SerializedName("programId")
    val programId: Int? = null,
    @field:SerializedName("programTitle")
    val programTitle: String? = null,
    @field:SerializedName("isFav")
    val isFav: Boolean? = null,
    @field:SerializedName("isSave")
    val isSave: Boolean? = null

)

