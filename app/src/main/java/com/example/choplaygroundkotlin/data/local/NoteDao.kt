package com.example.choplaygroundkotlin.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNote(noteCacheEntity: NoteCacheEntity) : Long

    @Query("SELECT * FROM notes")
    fun observeAllNoteItems(): LiveData<List<NoteCacheEntity>>

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}