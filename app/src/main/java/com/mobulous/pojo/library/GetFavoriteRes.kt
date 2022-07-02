package com.mobulous.pojo.fav

import com.google.gson.annotations.SerializedName

data class GetFavoriteRes(

    @field:SerializedName("data")
    val data: FavDataItem? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class FavDataItem(

    @field:SerializedName("favPrograms")
    val favPrograms: ArrayList<FavProgramsItem?>? = null,

    @field:SerializedName("favChapters")
    val favChapters: ArrayList<FavChaptersItem?>? = null
)

data class ChapterDetails(

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

    @field:SerializedName("has_access")
    val hasAccess: Boolean? = null,

    @field:SerializedName("searchKey")
    val searchKey: String? = null,

    @field:SerializedName("details_url")
    val detailsUrl: Any? = null,

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

data class ProgramDetails(

    @field:SerializedName("show_trailer")
    val showTrailer: String? = null,

    @field:SerializedName("categoryIdMongo")
    val categoryIdMongo: String? = null,

    @field:SerializedName("author")
    val author: Any? = null,

    @field:SerializedName("categoryTitle")
    val categoryTitle: String? = null,

    @field:SerializedName("description")
    val description: String? = null,

    @field:SerializedName("authorDescription")
    val authorDescription: Any? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("horizontal_preview")
    val horizontalPreview: String? = null,

    @field:SerializedName("chapters_count")
    val chaptersCount: Int? = null,

    @field:SerializedName("authorId")
    val authorId: Any? = null,

    @field:SerializedName("authorTitle")
    val authorTitle: Any? = null,

    @field:SerializedName("trailer")
    val trailer: Any? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("vertical_preview")
    val verticalPreview: Any? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("authorImage")
    val authorImage: Any? = null,

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

data class CategoryDetails(

    @field:SerializedName("image")
    val image: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("featured")
    val featured: Boolean? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("_id")
    val _id: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("lastsyncTime")
    val lastsyncTime: Long? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)

data class FavProgramsItem(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("programDetails")
    val programDetails: ProgramDetails? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null,

    @field:SerializedName("programId")
    val programId: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("categoryDetails")
    val categoryDetails: CategoryDetails? = null,

    @field:SerializedName("categoryId")
    val categoryId: Int? = null,

    @field:SerializedName("isSave")
    var isSave: Boolean? = false


)

data class FavChaptersItem(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("chapterId")
    val chapterId: Int? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("chapterDetails")
    val chapterDetails: ChapterDetails? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("isSave")
    var isSave: Boolean? = false
)
