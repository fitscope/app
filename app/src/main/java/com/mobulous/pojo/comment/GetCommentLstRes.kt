package com.mobulous.pojo.comment

import com.google.gson.annotations.SerializedName

data class GetCommentLstRes(

    @field:SerializedName("data")
    val data: ArrayList<CommentsDataItems?>? = null,

    @field:SerializedName("message")
    val message: String? = null,

    @field:SerializedName("status")
    val status: Int? = null
)

data class RepliesItem(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("chapterId")
    val chapterId: Int? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("commentId")
    val commentId: String? = null,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("user")
    val user: CommentUserDataObj? = null
)

data class CommentsDataItems(

    @field:SerializedName("createdAt")
    val createdAt: String? = null,

    @field:SerializedName("replies")
    val replies: ArrayList<RepliesItem?>? = null,

    @field:SerializedName("chapterId")
    val chapterId: Int? = null,

    @field:SerializedName("__v")
    val V: Int? = null,

    @field:SerializedName("comment")
    val comment: String? = null,

    @field:SerializedName("_id")
    val id: String? = null,

    @field:SerializedName("userId")
    val userId: Int? = null,

    @field:SerializedName("updatedAt")
    val updatedAt: String? = null,

    @field:SerializedName("user")
    val user: CommentUserDataObj? = null


)

data class CommentUserDataObj(
    val id: String? = null,
    val name: String? = null,
    val email: String? = null,
    val avatar_url: String? = null
)
