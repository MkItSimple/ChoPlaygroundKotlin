package com.example.choplaygroundkotlin.repositories

import androidx.lifecycle.LiveData
import com.example.choplaygroundkotlin.data.local.FolderCacheEntity
import com.example.choplaygroundkotlin.data.local.NoteItem
import com.example.choplaygroundkotlin.domain.Note

interface NoteListRepository {

    suspend fun insertNoteItem(noteItem: NoteItem)

    fun observeAllNoteItems(): LiveData<List<NoteItem>>
}