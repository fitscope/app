package com.mobulous.pojo.library

import com.google.gson.annotations.SerializedName

data class AddProgramToFavRes(

    @field:SerializedName("data")
    val data: ProgramToFavDataItem? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class ProgramToFavDataItem(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null,

    @field:SerializedName("categoryId")
    val categoryId: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
