package com.example.choplaygroundkotlin.repositories

import androidx.lifecycle.LiveData
import androidx.room.Delete
import androidx.room.Query
import com.example.choplaygroundkotlin.data.local.FolderItem
import com.example.choplaygroundkotlin.data.local.FolderWithNotes
import com.example.choplaygroundkotlin.data.local.NoteItem

interface NoteListRepository {

    // Note
    suspend fun insertNoteItem(noteItem: NoteItem)

    fun observeAllNoteItems(): LiveData<List<NoteItem>>

    suspend fun deleteAllNotes()


    // Folder
    suspend fun insertFolderItem(noteItem: FolderItem)

    fun observeAllFolderItems(): LiveData<List<FolderItem>>

    suspend fun deleteAllFolders()


    // Folders and Notes
    fun fetchFoldersWithNotes(): LiveData<List<FolderWithNotes>>





}