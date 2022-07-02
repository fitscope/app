package com.mobulous.pojo.dashboard.program

import com.google.gson.annotations.SerializedName
import com.mobulous.pojo.dashboard.ProgramCategoryDataItem

data class SeniorWorkoutRes(

    @field:SerializedName("data")
    val data: ArrayList<SeniorWorkoutDataItemPojo?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class SeniorWorkoutDataItemPojo(

    @field:SerializedName("cateImage")
    val cateImage: String? = null,

    @field:SerializedName("categoryData")
    val categoryData: ArrayList<ProgramCategoryDataItem?>? = null,

    @field:SerializedName("category")
    val category: String? = null
)
