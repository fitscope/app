package com.mobulous.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface UserDao {
    @Insert
    fun addDownload(value: UserDownloads)

    @Query("Delete from UserDownloads where chapterID = :chapterID ")
    fun removeChapter(chapterID: String): Int

    @Query("SELECT * from UserDownloads")
    fun getAllDownloads(): List<UserDownloads>

    @Query("Select * from UserDownloads where chapterID = :chapterID")
    fun isChapterAlreadyDownload(chapterID: String): Boolean
}