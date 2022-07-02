package com.mobulous.pojo.library

import com.google.gson.annotations.SerializedName

data class AddProgramToSaveRes(

    @field:SerializedName("data")
    val data: AddProgramToSaveDataItem? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class AddProgramToSaveDataItem(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null,

    @field:SerializedName("programId")
    val programId: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null
)
