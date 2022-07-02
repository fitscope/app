package com.mobulous.pojo.dashboard.classes.Ellipticals

import com.google.gson.annotations.SerializedName
import com.mobulous.pojo.dashboard.CommonCategoryDataItem
import com.mobulous.pojo.dashboard.CommonChaptersItem

data class ClassEllipticalRes(

    @field:SerializedName("data")
    val data: ArrayList<HomeEllipticalsDataItems?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class HomeEllipticalsDataItems(

    @field:SerializedName("cateImage")
    val cateImage: String? = null,

    @field:SerializedName("categoryData")
    val categoryData: ArrayList<CommonCategoryDataItem?>? = null,

    @field:SerializedName("category")
    val category: String? = null
)

