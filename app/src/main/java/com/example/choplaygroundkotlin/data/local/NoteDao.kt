package com.example.choplaygroundkotlin.data.local

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.choplaygroundkotlin.domain.Note

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertNoteItem(noteItem: NoteItem)

    @Query("SELECT * FROM notes")
    fun observeAllNoteItems(): LiveData<List<NoteItem>>

    @Query("DELETE FROM notes")
    suspend fun deleteAllNotes()
}