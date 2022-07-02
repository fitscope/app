package com.mobulous.pojo.fav

import com.google.gson.annotations.SerializedName

data class GetFavoriteLstRes(

	@field:SerializedName("data")
	val data: ArrayList<FavoriteLstDataItem?>? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class FavoriteLstDataItem(

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("chapterId")
	val chapterId: Int? = null,

	@field:SerializedName("__v")
	val V: Int? = null,

	@field:SerializedName("_id")
	val id: String? = null,

	@field:SerializedName("userName")
	val userName: String? = null,

	val title:String?=null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("fitScopeData")
	val fitScopeData: FitScopeData? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)

data class Versions(

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
	val versions: Versions? = null,

	@field:SerializedName("preview_image_url")
	val previewImageUrl: String? = null,

	@field:SerializedName("duration_in_seconds")
	val durationInSeconds: Int? = null
)
