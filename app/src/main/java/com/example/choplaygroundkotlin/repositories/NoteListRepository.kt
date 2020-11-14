package com.example.choplaygroundkotlin.repositories

import androidx.lifecycle.LiveData
import com.example.choplaygroundkotlin.data.local.FolderItem
import com.example.choplaygroundkotlin.data.local.NoteItem

interface NoteListRepository {

    // Note
    suspend fun insertNoteItem(noteItem: NoteItem)

    fun observeAllNoteItems(): LiveData<List<NoteItem>>

    // Folder
    suspend fun insertFolderItem(noteItem: FolderItem)

    fun observeAllFolderItems(): LiveData<List<FolderItem>>
}