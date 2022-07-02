package com.mobulous.pojo.comment.reply

import com.google.gson.annotations.SerializedName

data class CommentReplyRes(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Data(

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

	@field:SerializedName("userName")
	val userName: String? = null,

	@field:SerializedName("userId")
	val userId: String? = null,

	@field:SerializedName("updatedAt")
	val updatedAt: String? = null
)
