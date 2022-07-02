package com.mobulous.pojo.dashboard.program

import com.google.gson.annotations.SerializedName

data class SeniorWorkOutByIDRes(

    @field:SerializedName("data")
    val data: ArrayList<SeniorWorkOutDataItem?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class SeniorWorkOutChaptersItem(

    @field:SerializedName("enroll_image")
    val enrollImage: String? = null,

    @field:SerializedName("duration")
    val duration: String? = null,

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
    val availableAt: Any? = null
)

data class SeniorWorkOutAuthor(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("title")
    val title: String? = null
)

data class SeniorWorkOutDataItem(

    @field:SerializedName("show_trailer")
    val showTrailer: Boolean? = null,

    @field:SerializedName("chapters")
    val chapters: ArrayList<SeniorWorkOutChaptersItem?>? = null,

    @field:SerializedName("author")
    val author: SeniorWorkOutAuthor? = null,

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
