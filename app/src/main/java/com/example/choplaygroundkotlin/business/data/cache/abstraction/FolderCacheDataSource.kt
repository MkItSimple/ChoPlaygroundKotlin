package com.example.choplaygroundkotlin.business.data.cache.abstraction

import com.example.choplaygroundkotlin.business.domain.model.Folder

interface FolderCacheDataSource{

    suspend fun insertFolder(folder: Folder): Long

    suspend fun deleteFolder(primaryKey: String): Int

    suspend fun deleteFolders(folders: List<Folder>): Int

    suspend fun updateFolder(
        primaryKey: String,
        newFolderName: String,
        newNotesCount: Int?,
        timestamp: String?
    ): Int

    suspend fun searchFolders(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<Folder>

    suspend fun getAllFolders(): List<Folder>

    suspend fun searchFolderById(id: String): Folder?

    suspend fun getNumFolders(): Int

    suspend fun insertFolders(folders: List<Folder>): LongArray
}






