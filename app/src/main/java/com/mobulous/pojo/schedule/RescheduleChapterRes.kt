package com.mobulous.pojo.schedule

import com.google.gson.annotations.SerializedName

data class RescheduleChapterRes(

    @field:SerializedName("data")
    val data: Any? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)
