package com.mobulous.pojo.saveChapter

data class SaveChapterPost(
    val userName: String,
    val userId: String,
    val chapterId: String,
    val authorizationToken: String,
    val title:String
)
