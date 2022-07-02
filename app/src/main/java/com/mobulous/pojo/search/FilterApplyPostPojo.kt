package com.mobulous.pojo.search

import com.google.gson.annotations.SerializedName

data class FilterApplyPostPojo(

	@field:SerializedName("difficulty")
	val difficulty: List<String?>? = null,

	@field:SerializedName("goal")
	val goal: List<String?>? = null,

	@field:SerializedName("music")
	val music: List<String?>? = null,

	@field:SerializedName("trainer")
	val trainer: List<String?>? = null,

	@field:SerializedName("fromTime")
	val fromTime: Int? = null,

	@field:SerializedName("toTime")
	val toTime: Int? = null,

	@field:SerializedName("authorTitle")
	val authorTitle: List<String?>? = null
)
