package com.example.choplaygroundkotlin.framework.datasource.cache.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.choplaygroundkotlin.framework.datasource.cache.model.FolderCacheEntity

@Database(entities = [FolderCacheEntity::class], version = 1)
abstract class NoteDatabase: RoomDatabase() {

    abstract fun folderDao(): FolderDao

    companion object{
        val DATABASE_NAME: String = "note_db"
    }


}