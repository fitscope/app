package com.mobulous.pojo.library

data class RemoveProgramFromFavRes(
	val data: Data? = null,
	val message: String? = null,
	val status: Int? = null
)

data class Data(
	val deletedCount: Int? = null,
	val ok: Int? = null,
	val N: Int? = null
)

