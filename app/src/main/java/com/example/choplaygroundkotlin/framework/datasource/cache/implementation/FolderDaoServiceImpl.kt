package com.example.choplaygroundkotlin.framework.datasource.cache.implementation

import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import com.example.choplaygroundkotlin.framework.datasource.cache.abstraction.FolderDaoService
import com.example.choplaygroundkotlin.framework.datasource.cache.database.FolderDao
import com.example.choplaygroundkotlin.framework.datasource.cache.database.returnOrderedQuery
import com.example.choplaygroundkotlin.framework.datasource.cache.mappers.FolderCacheMapper
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class FolderDaoServiceImpl
@Inject
constructor(
    private val folderDao: FolderDao,
    private val folderMapper: FolderCacheMapper,
    private val dateUtil: DateUtil
): FolderDaoService {

    override suspend fun insertFolder(folder: Folder): Long {
        return folderDao.insertFolder(folderMapper.mapToEntity(folder))
    }

    override suspend fun insertFolders(folders: List<Folder>): LongArray {
        return folderDao.insertFolders(
            folderMapper.folderListToEntityList(folders)
        )
    }

    override suspend fun searchFolderById(id: String): Folder? {
        return folderDao.searchFolderById(id)?.let { folder ->
            folderMapper.mapFromEntity(folder)
        }
    }

    override suspend fun updateFolder(
        primaryKey: String,
        folder_name: String,
        timestamp: String?
    ): Int {
        return if(timestamp != null){
            folderDao.updateFolder(
                primaryKey = primaryKey,
                folder_name = folder_name,
                updated_at = timestamp
            )
        }else{
            folderDao.updateFolder(
                primaryKey = primaryKey,
                folder_name = folder_name,
                updated_at = dateUtil.getCurrentTimestamp()
            )
        }

    }

    override suspend fun deleteFolder(primaryKey: String): Int {
        return folderDao.deleteFolder(primaryKey)
    }

    override suspend fun deleteFolders(folders: List<Folder>): Int {
        val ids = folders.mapIndexed {index, value -> value.id}
        return folderDao.deleteFolders(ids)
    }

    override suspend fun searchFolders(): List<Folder> {
        return folderMapper.entityListToFolderList(
            folderDao.searchFolders()
        )
    }

    override suspend fun getAllFolders(): List<Folder> {
        return folderMapper.entityListToFolderList(folderDao.getAllFolders())
    }

    override suspend fun searchFoldersOrderByDateDESC(
        query: String,
        page: Int,
        pageSize: Int
    ): List<Folder> {
        return folderMapper.entityListToFolderList(
            folderDao.searchFoldersOrderByDateDESC(
                query = query,
                page = page,
                pageSize = pageSize
            )
        )
    }

    override suspend fun searchFoldersOrderByDateASC(
        query: String,
        page: Int,
        pageSize: Int
    ): List<Folder> {
        return folderMapper.entityListToFolderList(
            folderDao.searchFoldersOrderByDateASC(
                query = query,
                page = page,
                pageSize = pageSize
            )
        )
    }

    override suspend fun searchFoldersOrderByTitleDESC(
        query: String,
        page: Int,
        pageSize: Int
    ): List<Folder> {
        return folderMapper.entityListToFolderList(
            folderDao.searchFoldersOrderByTitleDESC(
                query = query,
                page = page,
                pageSize = pageSize
            )
        )
    }

    override suspend fun searchFoldersOrderByTitleASC(
        query: String,
        page: Int,
        pageSize: Int
    ): List<Folder> {
        return folderMapper.entityListToFolderList(
            folderDao.searchFoldersOrderByTitleASC(
                query = query,
                page = page,
                pageSize = pageSize
            )
        )
    }

    override suspend fun getNumFolders(): Int {
        return folderDao.getNumFolders()
    }

    override suspend fun returnOrderedQuery(
        query: String,
        filterAndOrder: String,
        page: Int
    ): List<Folder> {
        return folderMapper.entityListToFolderList(
            folderDao.returnOrderedQuery(
                query = query,
                page = page,
                filterAndOrder = filterAndOrder
            )
        )
    }
}













