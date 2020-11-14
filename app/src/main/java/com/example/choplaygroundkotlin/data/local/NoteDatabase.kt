package com.example.choplaygroundkotlin.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [FolderCacheEntity::class, NoteCacheEntity::class],
    version = 2
)
abstract class NoteDatabase : RoomDatabase() {
    abstract fun folderDao(): FolderDao
    abstract fun noteDao(): NoteDao
}