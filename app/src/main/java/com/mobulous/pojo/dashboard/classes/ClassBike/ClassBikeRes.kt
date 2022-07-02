package com.mobulous.pojo.dashboard.classes.ClassBike

import com.google.gson.annotations.SerializedName
import com.mobulous.pojo.dashboard.CommonCategoryDataItem
import com.mobulous.pojo.dashboard.CommonChaptersItem

data class ClassBikeRes(

    @field:SerializedName("data")
    val data: ArrayList<ClassBikeDataItems?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class ClassBikeDataItems(

    @field:SerializedName("categoryData")
    val categoryData: ArrayList<CommonCategoryDataItem?>? = null,

    @field:SerializedName("category")
    val category: String? = null,
    @field:SerializedName("cateImage")
    val cateImage: String? = null


)




