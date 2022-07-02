package com.mobulous.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "UserDownloads")
data class UserDownloads(
    @PrimaryKey val chapterID: String,
    @ColumnInfo(name = "user") val data: String
)