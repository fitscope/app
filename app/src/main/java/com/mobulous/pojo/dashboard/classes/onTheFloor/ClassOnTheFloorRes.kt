package com.mobulous.pojo.dashboard.classes.onTheFloor

import com.google.gson.annotations.SerializedName
import com.mobulous.pojo.dashboard.CommonCategoryDataItem

data class ClassOnTheFloorRes(

    @field:SerializedName("data")
    val data: ArrayList<ClassOnTheFlorrDataItem?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class ClassOnTheFlorrDataItem(

    @field:SerializedName("cateImage")
    val cateImage: String? = null,

    @field:SerializedName("categoryData")
    val categoryData: ArrayList<CommonCategoryDataItem?>? = null,

    @field:SerializedName("category")
    val category: String? = null
)

