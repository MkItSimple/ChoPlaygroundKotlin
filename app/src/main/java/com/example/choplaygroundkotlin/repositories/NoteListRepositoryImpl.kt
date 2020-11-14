package com.example.choplaygroundkotlin.repositories

import androidx.lifecycle.LiveData
import com.example.choplaygroundkotlin.data.local.NoteDao
import com.example.choplaygroundkotlin.data.local.NoteItem
import com.example.choplaygroundkotlin.domain.Note
import javax.inject.Inject

class NoteListRepositoryImpl @Inject constructor(
    private val noteDao: NoteDao
) : NoteListRepository {

    override suspend fun insertNoteItem(noteItem: NoteItem) {
        noteDao.insertNoteItem(noteItem)
    }

    override fun observeAllNoteItems(): LiveData<List<NoteItem>> {
        return noteDao.observeAllNoteItems()
    }
}