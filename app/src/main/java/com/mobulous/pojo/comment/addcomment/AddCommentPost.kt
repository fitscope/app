package com.mobulous.pojo.comment.addcomment

import com.google.gson.annotations.SerializedName

data class AddCommentPost(

    @field:SerializedName("chapterId")
    val chapterId: String? = null,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("userName")
    val userName: String? = null,

    @field:SerializedName("userId")
    val userId: String? = null
)
