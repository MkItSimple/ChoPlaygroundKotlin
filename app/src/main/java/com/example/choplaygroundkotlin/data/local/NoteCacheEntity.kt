package com.example.choplaygroundkotlin.data.local

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "notes",
    // with this we now can't add note if folder_id don't exist in folders table
    foreignKeys = [
        ForeignKey(
            entity = FolderCacheEntity::class,
            parentColumns = ["folder_id"],
            childColumns = ["note_folder_id"]
        )
    ]
)
data class NoteCacheEntity(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    var title: String,
    var note_folder_id: String
)