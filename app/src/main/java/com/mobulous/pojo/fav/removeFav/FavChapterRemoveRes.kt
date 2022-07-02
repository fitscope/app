package com.mobulous.pojo.fav.removeFav

import com.google.gson.annotations.SerializedName

data class FavChapterRemoveRes(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("status")
	val status: Int? = null
)

data class Data(

	@field:SerializedName("deletedCount")
	val deletedCount: Int? = null,

	@field:SerializedName("ok")
	val ok: Int? = null,

	@field:SerializedName("n")
	val N: Int? = null
)
