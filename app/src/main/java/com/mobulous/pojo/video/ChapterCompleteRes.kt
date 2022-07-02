package com.mobulous.pojo.video

import com.google.gson.annotations.SerializedName

data class ChapterCompleteRes(

	@field:SerializedName("data")
	val data: ChapterCompleteDataItem? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class ChapterCompleteDataItem(

	@field:SerializedName("date")
	val date: String? = null,

	@field:SerializedName("dateValue")
	val dateValue: String? = null,

	@field:SerializedName("timeInMint")
	val timeInMint: Int? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("userName")
	val userName: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("fitScopeData")
	val fitScopeData: FitScopeData? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("chapterId")
	val chapterId: Int? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("time")
	val time: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class ChapterCompleteVersions(

	@field:SerializedName("sd")
	val sd: String? = null,

	@field:SerializedName("md")
	val md: String? = null,

	@field:SerializedName("hd")
	val hd: String? = null,

	@field:SerializedName("hls")
	val hls: String? = null
)

data class FitScopeData(

	@field:SerializedName("duration")
	val duration: String? = null,

	@field:SerializedName("subtitles")
	val subtitles: List<Any?>? = null,

	@field:SerializedName("short_description")
	val shortDescription: String? = null,

	@field:SerializedName("versions")
	val versions: ChapterCompleteVersions? = null,

	@field:SerializedName("preview_image_url")
	val previewImageUrl: String? = null,

	@field:SerializedName("duration_in_seconds")
	val durationInSeconds: Int? = null
)
