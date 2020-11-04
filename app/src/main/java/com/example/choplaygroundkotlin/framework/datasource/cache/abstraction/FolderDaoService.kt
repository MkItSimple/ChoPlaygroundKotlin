package com.example.choplaygroundkotlin.framework.datasource.cache.abstraction

import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.framework.datasource.cache.database.FOLDER_PAGINATION_PAGE_SIZE

interface FolderDaoService {

    suspend fun insertFolder(folder: Folder): Long

    suspend fun insertFolders(folders: List<Folder>): LongArray

    suspend fun searchFolderById(id: String): Folder?

    suspend fun updateFolder(
        primaryKey: String,
        folder_name: String,
        timestamp: String?
    ): Int

    suspend fun deleteFolder(primaryKey: String): Int

    suspend fun deleteFolders(folders: List<Folder>): Int

    suspend fun searchFolders(): List<Folder>

    suspend fun getAllFolders(): List<Folder>

    suspend fun searchFoldersOrderByDateDESC(
        query: String,
        page: Int,
        pageSize: Int = FOLDER_PAGINATION_PAGE_SIZE
    ): List<Folder>

    suspend fun searchFoldersOrderByDateASC(
        query: String,
        page: Int,
        pageSize: Int = FOLDER_PAGINATION_PAGE_SIZE
    ): List<Folder>

    suspend fun searchFoldersOrderByTitleDESC(
        query: String,
        page: Int,
        pageSize: Int = FOLDER_PAGINATION_PAGE_SIZE
    ): List<Folder>

    suspend fun searchFoldersOrderByTitleASC(
        query: String,
        page: Int,
        pageSize: Int = FOLDER_PAGINATION_PAGE_SIZE
    ): List<Folder>

    suspend fun getNumFolders(): Int

    suspend fun returnOrderedQuery(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<Folder>
}












