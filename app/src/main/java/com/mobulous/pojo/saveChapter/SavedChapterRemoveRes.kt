package com.mobulous.pojo.saveChapter

import com.google.gson.annotations.SerializedName

data class SavedChapterRemoveRes(

    @field:SerializedName("data")
    val data: SavedData? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class SavedData(

    @field:SerializedName("deletedCount")
    val deletedCount: Int? = null,

    @field:SerializedName("ok")
    val ok: Int? = null,

    @field:SerializedName("n")
    val N: Int? = null
)
