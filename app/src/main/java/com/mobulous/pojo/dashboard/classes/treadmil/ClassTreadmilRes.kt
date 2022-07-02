package com.mobulous.pojo.dashboard.classes.treadmil

import com.google.gson.annotations.SerializedName
import com.mobulous.pojo.dashboard.CommonCategoryDataItem

data class ClassTreadmilRes(

	@field:SerializedName("data")
	val data: ArrayList<ClassTreadDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ClassTreadDataItem(

	@field:SerializedName("cateImage")
	val cateImage: String? = null,

	@field:SerializedName("categoryData")
	val categoryData: ArrayList<CommonCategoryDataItem?>? = null,

	@field:SerializedName("category")
	val category: String? = null
)

