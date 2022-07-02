package com.mobulous.pojo.dashboard.classes

import com.google.gson.annotations.SerializedName
import com.mobulous.pojo.dashboard.CommonCategoryDataItem

data class ClassRowerRes(

    @field:SerializedName("data")
    val data: ArrayList<ClassRowerDataItems?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class ClassRowerDataItems(

    @field:SerializedName("cateImage")
    val cateImage: String? = null,

    @field:SerializedName("categoryData")
    val categoryData: ArrayList<CommonCategoryDataItem?>? = null,

    @field:SerializedName("category")
    val category: String? = null
)

