package com.example.choplaygroundkotlin.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes")
data class NoteItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = null,
    var title: String
)