package com.mobulous.pojo.dashboard.program

import com.google.gson.annotations.SerializedName

data class WeightPojo(

	@field:SerializedName("WeightPojo")
	val weightPojo: ArrayList<WeightPojoItem?>? = null
)

data class ChaptersItem(

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
	val detailsUrl: Any? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("available_at")
	val availableAt: Any? = null,

	@field:SerializedName("show_trailer")
	val showTrailer: Boolean? = null,

	@field:SerializedName("chapters")
	val chapters: List<ChaptersItem?>? = null,

	@field:SerializedName("author")
	val author: Any? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("horizontal_preview")
	val horizontalPreview: String? = null,

	@field:SerializedName("chapters_count")
	val chaptersCount: Int? = null,

	@field:SerializedName("trailer")
	val trailer: Any? = null,

	@field:SerializedName("vertical_preview")
	val verticalPreview: Any? = null,

	@field:SerializedName("description_html")
	val descriptionHtml: String? = null
)

data class CategoriesItem(

	@field:SerializedName("show_trailer")
	val showTrailer: Boolean? = null,

	@field:SerializedName("chapters")
	val chapters: List<ChaptersItem?>? = null,

	@field:SerializedName("author")
	val author: Any? = null,

	@field:SerializedName("in_favorites")
	val inFavorites: Boolean? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("has_access")
	val hasAccess: Boolean? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("horizontal_preview")
	val horizontalPreview: String? = null,

	@field:SerializedName("chapters_count")
	val chaptersCount: Int? = null,

	@field:SerializedName("trailer")
	val trailer: Any? = null,

	@field:SerializedName("vertical_preview")
	val verticalPreview: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("description_html")
	val descriptionHtml: String? = null
)

data class Author(

	@field:SerializedName("image")
	val image: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null
)

data class WeightPojoItem(

	@field:SerializedName("categories")
	val categories: List<CategoriesItem?>? = null,

	@field:SerializedName("mainkey")
	val mainkey: String? = null
)
