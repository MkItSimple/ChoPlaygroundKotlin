package com.example.choplaygroundkotlin.business.data.cache.implementation

import com.example.choplaygroundkotlin.business.data.cache.abstraction.FolderCacheDataSource
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.framework.datasource.cache.abstraction.FolderDaoService
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderCacheDataSourceImpl
@Inject
constructor(
    private val folderDaoService: FolderDaoService
): FolderCacheDataSource {

    override suspend fun insertFolder(folder: Folder): Long {
        return folderDaoService.insertFolder(folder)
    }

    override suspend fun deleteFolder(primaryKey: String): Int {
        return folderDaoService.deleteFolder(primaryKey)
    }

    override suspend fun deleteFolders(folders: List<Folder>): Int {
        return folderDaoService.deleteFolders(folders)
    }

    override suspend fun updateFolder(
        primaryKey: String,
        newFolderName: String,
        timestamp: String?
    ): Int {
        return folderDaoService.updateFolder(
            primaryKey,
            newFolderName,
            timestamp
        )
    }

    override suspend fun searchFolders(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<Folder> {
        return folderDaoService.returnOrderedQuery(
            query, filterAndOrder, page
        )
    }

    override suspend fun getAllFolders(): List<Folder> {
        return folderDaoService.getAllFolders()
    }

    override suspend fun searchFolderById(id: String): Folder? {
        return folderDaoService.searchFolderById(id)
    }

    override suspend fun getNumFolders(): Int {
        return folderDaoService.getNumFolders()
    }

    override suspend fun insertFolders(folders: List<Folder>): LongArray{
        return folderDaoService.insertFolders(folders)
    }
}





















