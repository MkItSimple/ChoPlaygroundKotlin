package com.example.choplaygroundkotlin.data.local

import androidx.room.Embedded
import androidx.room.Relation

data class FolderWithNotes(
    @Embedded
    val folderCacheEntity: FolderCacheEntity,
    @Relation(
        parentColumn = "folder_id",
        entityColumn = "note_folder_id"
    )
    val notes: List<NoteCacheEntity>
)