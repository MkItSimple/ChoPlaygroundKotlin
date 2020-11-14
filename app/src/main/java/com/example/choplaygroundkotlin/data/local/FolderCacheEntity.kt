package com.example.choplaygroundkotlin.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "folders")
data class FolderCacheEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "folder_id")
    val folder_id: String,
    var folder_name: String
)