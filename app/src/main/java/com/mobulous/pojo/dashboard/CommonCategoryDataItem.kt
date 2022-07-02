package com.mobulous.pojo.dashboard

import com.google.gson.annotations.SerializedName

data class CommonCategoryDataItem(

    @field:SerializedName("chapters")
    val chapters: ArrayList<CommonChaptersItem?>? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("categoryId")
    val categoryId: String? = null,

    @field:SerializedName("programId")
    val programId: String? = null,
    @field:SerializedName("programTitle")
    val programTitle: String? = null,

    @field:SerializedName("programImage")
    val programImage: String? = null,

    @field:SerializedName("isFav")
    val isFav: Boolean? = null,

    @field:SerializedName("isSave")
    val isSave: Boolean? = null

)