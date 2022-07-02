package com.mobulous.pojo.dashboard.classes.home

import com.google.gson.annotations.SerializedName

data class HomeParentCategoriesNameRes(

	@field:SerializedName("HomeParentCategoriesNameRes")
	val homeParentCategoriesNameRes: List<HomeParentCategoriesNameResItem?>? = null
)

data class HomeParentCategoriesNameResItem(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("featured")
	val featured: Boolean? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
)
