package com.example.choplaygroundkotlin.repositories

import androidx.lifecycle.LiveData
import com.example.choplaygroundkotlin.data.local.FolderCacheEntity
import com.example.choplaygroundkotlin.data.local.FolderWithNotes
import com.example.choplaygroundkotlin.data.local.NoteCacheEntity
import com.example.choplaygroundkotlin.domain.Note

interface NoteListRepository {

    // Note
    suspend fun insertNote(note: Note): Long

    fun observeAllNoteItems(): LiveData<List<NoteCacheEntity>>

    suspend fun deleteAllNotes()


    // Folder
    suspend fun insertFolderItem(noteCacheEntity: FolderCacheEntity)

    fun observeAllFolderItems(): LiveData<List<FolderCacheEntity>>

    suspend fun deleteAllFolders()


    // Folders and Notes
    fun fetchFoldersWithNotes(): LiveData<List<FolderWithNotes>>





}