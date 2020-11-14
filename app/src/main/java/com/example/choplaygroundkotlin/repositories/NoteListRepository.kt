package com.example.choplaygroundkotlin.repositories

import androidx.lifecycle.LiveData
import com.example.choplaygroundkotlin.data.local.FolderCacheEntity
import com.example.choplaygroundkotlin.data.local.FolderWithNotes
import com.example.choplaygroundkotlin.data.local.NoteCacheEntity

interface NoteListRepository {

    // Note
    suspend fun insertNoteItem(noteCacheEntity: NoteCacheEntity)

    fun observeAllNoteItems(): LiveData<List<NoteCacheEntity>>

    suspend fun deleteAllNotes()


    // Folder
    suspend fun insertFolderItem(noteCacheEntity: FolderCacheEntity)

    fun observeAllFolderItems(): LiveData<List<FolderCacheEntity>>

    suspend fun deleteAllFolders()


    // Folders and Notes
    fun fetchFoldersWithNotes(): LiveData<List<FolderWithNotes>>





}