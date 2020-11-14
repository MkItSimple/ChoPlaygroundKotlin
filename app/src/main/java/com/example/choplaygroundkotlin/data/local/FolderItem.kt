package com.example.choplaygroundkotlin.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "folders")
data class FolderItem(
    @PrimaryKey(autoGenerate = true)
    val folder_id: Int? = null,
    var folder_name: String
)