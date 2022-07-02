package com.mobulous.pojo.dashboard

import com.google.gson.annotations.SerializedName

data class CommonChaptersItem(

    @field:SerializedName("preview_image")
    val previewImage: String? = null,

    @field:SerializedName("categoryIdMongo")
    val categoryIdMongo: String? = null,

    @SerializedName("categoryTitle")
    val categoryTitle: String? = null,

    @field:SerializedName("has_access")
    val hasAccess: Boolean? = null,

    @field:SerializedName("searchKey")
    val searchKey: String? = null,

    @field:SerializedName("type")
    val type: String? = null,

    @field:SerializedName("details_url")
    val detailsUrl: Any? = null,

    @field:SerializedName("title")
    val title: String? = null,

    @field:SerializedName("enroll_image")
    val enrollImage: String? = null,

    @field:SerializedName("duration")
    val duration: String? = null,

    @field:SerializedName("programTitle")
    val programTitle: String? = null,

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("_id")
    val _id: String? = null,

    @field:SerializedName("id")
    val id: Int? = null,

    @field:SerializedName("lastsyncTime")
    val lastsyncTime: Long? = null,

    @field:SerializedName("available_at")
    val availableAt: Any? = null,

    @field:SerializedName("categoryId")
    val categoryId: Int? = null,

    @field:SerializedName("programId")
    val programId: Int? = null,

    @field:SerializedName("programIdMongo")
    val programIdMongo: String? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)