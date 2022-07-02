package com.mobulous.pojo.saveChapter

data class SavedChapterRes(
	val data: Data? = null,
	val message: String? = null,
	val status: Int? = null
)

data class Data(
	val createdAt: String? = null,
	val chapterId: Int? = null,
	val V: Int? = null,
	val id: String? = null,
	val userName: String? = null,
	val userId: String? = null,
	val fitScopeData: FitScopeData? = null,
	val updatedAt: String? = null
)

data class Versions(
	val sd: String? = null,
	val md: String? = null,
	val hd: String? = null,
	val hls: String? = null
)

data class FitScopeData(
	val duration: String? = null,
	val subtitles: List<Any?>? = null,
	val shortDescription: String? = null,
	val versions: Versions? = null,
	val previewImageUrl: String? = null,
	val durationInSeconds: Int? = null
)

