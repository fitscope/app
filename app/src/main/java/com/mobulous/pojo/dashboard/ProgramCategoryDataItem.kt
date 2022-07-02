package com.mobulous.pojo.dashboard

import com.google.gson.annotations.SerializedName

data class ProgramCategoryDataItem(

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

    @field:SerializedName("programImage")
    val programImage: String? = null,

    @field:SerializedName("isFav")
    val isFav: Boolean? = null,

    @field:SerializedName("categoryId")
    val categoryId: Int? = null,

    @field:SerializedName("programId")
    val programId: Int? = null,

    @field:SerializedName("isSave")
    val isSave: Boolean? = null
)