package com.mobulous.pojo.library

import com.google.gson.annotations.SerializedName

data class ChapterRemoveFromSaveRes(

    @field:SerializedName("data")
    val data: RemovedChapDataItem? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class RemovedChapDataItem(

    @field:SerializedName("deletedCount")
    val deletedCount: Int? = null,

    @field:SerializedName("ok")
    val ok: Int? = null,

    @field:SerializedName("n")
    val N: Int? = null
)
