package com.mobulous.pojo.schedule

data class RemoveFromScheduleRes(
	val data: Data? = null,
	val message: String? = null,
	val status: Int? = null
)

data class Data(
	val deletedCount: Int? = null,
	val ok: Int? = null,
	val n: Int? = null
)

