package com.mobulous.pojo.search

import com.google.gson.annotations.SerializedName

data class SearchByQueryResultsRes(

    @field:SerializedName("data")
    val data: SearchedResultDataItem? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class SearchedResultDataItem(

    @field:SerializedName("chapters")
    val chapters: ArrayList<SearchedChaptersItem?>? = null,

    @field:SerializedName("programs")
    val programs: ArrayList<SearchedProgramsItem?>? = null,

    @SerializedName("totalLength")
    val totalLength: String? = null
)

data class SearchedChaptersItem(

    @field:SerializedName("longDescription")
    val longDescription: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("duration")
    val duration: Int? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("music")
    val music: String? = null,

    @field:SerializedName("trainer")
    val trainer: String? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("programImage")
    val programImage: String? = null,

    @field:SerializedName("available_at")
    val availableAt: Any? = null,

    @field:SerializedName("programIdMongo")
    val programIdMongo: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("goal")
    val goal: String? = null,

    @field:SerializedName("preview_image")
    val previewImage: String? = null,

    @field:SerializedName("categoryIdMongo")
    val categoryIdMongo: String? = null,

    @field:SerializedName("categoryTitle")
    val categoryTitle: String? = null,

    @field:SerializedName("authorDescription")
    val authorDescription: String? = null,

    @field:SerializedName("has_access")
    val hasAccess: Boolean? = null,

    @field:SerializedName("searchKey")
    val searchKey: String? = null,

    @field:SerializedName("shortDescription")
    val shortDescription: String? = null,

    @field:SerializedName("details_url")
    val detailsUrl: Any? = null,

    @field:SerializedName("authorId")
    val authorId: Int? = null,

    @field:SerializedName("videoData")
    val videoData: String? = null,

    @field:SerializedName("authorTitle")
    val authorTitle: String? = null,

    @field:SerializedName("difficulty")
    val difficulty: String? = null,

    @field:SerializedName("enroll_image")
    val enrollImage: String? = null,

    @field:SerializedName("programTitle")
    val programTitle: String? = null,

    @field:SerializedName("_id")
    val _id: String? = null,

    @field:SerializedName("lastsyncTime")
    val lastsyncTime: Long? = null,

    @field:SerializedName("categoryId")
    val categoryId: Int? = null,

    @field:SerializedName("programId")
    val programId: Int? = null
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

data class SearchedProgramsItem(

    @field:SerializedName("show_trailer")
    val showTrailer: String? = null,

    @field:SerializedName("categoryIdMongo")
    val categoryIdMongo: String? = null,

    @field:SerializedName("author")
    val author: Author? = null,

    @field:SerializedName("categoryTitle")
    val categoryTitle: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("authorDescription")
    val authorDescription: String? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("horizontal_preview")
    val horizontalPreview: String? = null,

    @field:SerializedName("chapters_count")
    val chaptersCount: Int? = null,

    @field:SerializedName("authorId")
    val authorId: Int? = null,

    @field:SerializedName("authorTitle")
    val authorTitle: String? = null,

    @field:SerializedName("trailer")
    val trailer: Any? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("vertical_preview")
    val verticalPreview: Any? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("authorImage")
    val authorImage: String? = null,

    @field:SerializedName("_id")
    val _id: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("description_html")
    val descriptionHtml: String? = null,

    @field:SerializedName("lastsyncTime")
    val lastsyncTime: Long? = null,

    @field:SerializedName("categoryId")
    val categoryId: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
