package com.mobulous.pojo.fav

import com.google.gson.annotations.SerializedName

data class FavChapterRes(

	@field:SerializedName("enroll_image")
	val enrollImage: String? = null,

	@field:SerializedName("duration")
	val duration: Int? = null,

	@field:SerializedName("preview_image")
	val previewImage: String? = null,

	@field:SerializedName("in_favorites")
	val inFavorites: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("has_access")
	val hasAccess: Boolean? = null,

	@field:SerializedName("type")
	val type: String? = null,

	@field:SerializedName("details_url")
	val detailsUrl: String? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("available_at")
	val availableAt: Any? = null
)
