package com.example.choplaygroundkotlin.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "folders"
)
data class FolderCacheEntity(

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "folder_id")
    var folder_id: Long = 0L,

    @ColumnInfo(name = "folder_name")
    var folder_name: String,

    @ColumnInfo(name = "notes_count")
    var notes_count: Int
)