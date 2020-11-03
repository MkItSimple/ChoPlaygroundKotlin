package com.example.choplaygroundkotlin.framework.datasource.cache.database

import androidx.room.*
import com.example.choplaygroundkotlin.framework.datasource.cache.model.FolderCacheEntity

const val FOLDER_ORDER_ASC: String = ""
const val FOLDER_ORDER_DESC: String = "-"
const val FOLDER_FILTER_TITLE = "folder_name"
const val FOLDER_FILTER_DATE_CREATED = "created_at"

const val ORDER_BY_ASC_DATE_UPDATED = FOLDER_ORDER_ASC + FOLDER_FILTER_DATE_CREATED
const val ORDER_BY_DESC_DATE_UPDATED = FOLDER_ORDER_DESC + FOLDER_FILTER_DATE_CREATED
const val ORDER_BY_ASC_TITLE = FOLDER_ORDER_ASC + FOLDER_FILTER_TITLE
const val ORDER_BY_DESC_TITLE = FOLDER_ORDER_DESC + FOLDER_FILTER_TITLE

const val FOLDER_PAGINATION_PAGE_SIZE = 30

@Dao
interface FolderDao {

    @Insert
    suspend fun insertFolder(folder: FolderCacheEntity): Long

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertFolders(folders: List<FolderCacheEntity>): LongArray

    @Query("SELECT * FROM folders WHERE id = :id")
    suspend fun searchFolderById(id: String): FolderCacheEntity?

    @Query("DELETE FROM folders WHERE id IN (:ids)")
    suspend fun deleteFolders(ids: List<String>): Int

    @Query("DELETE FROM folders")
    suspend fun deleteAllFolders()

    @Query("SELECT * FROM folders")
    suspend fun getAllFolders(): List<FolderCacheEntity>

    @Query("""
        UPDATE folders 
        SET 
        folder_name = :folder_name, 
        notes_count = :notes_count,
        updated_at = :updated_at
        WHERE id = :primaryKey
        """)
    suspend fun updateFolder(
        primaryKey: String,
        folder_name: String,
        notes_count: Int?,
        updated_at: String
    ): Int

    @Query("DELETE FROM folders WHERE id = :primaryKey")
    suspend fun deleteFolder(primaryKey: String): Int

    @Query("SELECT * FROM folders")
    suspend fun searchFolders(): List<FolderCacheEntity>

    @Query("""
        SELECT * FROM folders 
        WHERE folder_name LIKE '%' || :query || '%' 
        OR notes_count LIKE '%' || :query || '%' 
        ORDER BY updated_at DESC LIMIT (:page * :pageSize)
        """)
    suspend fun searchFoldersOrderByDateDESC(
        query: String,
        page: Int,
        pageSize: Int = FOLDER_PAGINATION_PAGE_SIZE
    ): List<FolderCacheEntity>

    @Query("""
        SELECT * FROM folders 
        WHERE folder_name LIKE '%' || :query || '%' 
        OR notes_count LIKE '%' || :query || '%' 
        ORDER BY updated_at ASC LIMIT (:page * :pageSize)
        """)
    suspend fun searchFoldersOrderByDateASC(
        query: String,
        page: Int,
        pageSize: Int = FOLDER_PAGINATION_PAGE_SIZE
    ): List<FolderCacheEntity>

    @Query("""
        SELECT * FROM folders 
        WHERE folder_name LIKE '%' || :query || '%' 
        OR notes_count LIKE '%' || :query || '%' 
        ORDER BY folder_name DESC LIMIT (:page * :pageSize)
        """)
    suspend fun searchFoldersOrderByTitleDESC(
        query: String,
        page: Int,
        pageSize: Int = FOLDER_PAGINATION_PAGE_SIZE
    ): List<FolderCacheEntity>

    @Query("""
        SELECT * FROM folders 
        WHERE folder_name LIKE '%' || :query || '%' 
        OR notes_count LIKE '%' || :query || '%' 
        ORDER BY folder_name ASC LIMIT (:page * :pageSize)
        """)
    suspend fun searchFoldersOrderByTitleASC(
        query: String,
        page: Int,
        pageSize: Int = FOLDER_PAGINATION_PAGE_SIZE
    ): List<FolderCacheEntity>


    @Query("SELECT COUNT(*) FROM folders")
    suspend fun getNumFolders(): Int
}


suspend fun FolderDao.returnOrderedQuery(
    query: String,
    filterAndOrder: String,
    page: Int
): List<FolderCacheEntity> {

    when{

        filterAndOrder.contains(ORDER_BY_DESC_DATE_UPDATED) ->{
            return searchFoldersOrderByDateDESC(
                query = query,
                page = page)
        }

        filterAndOrder.contains(ORDER_BY_ASC_DATE_UPDATED) ->{
            return searchFoldersOrderByDateASC(
                query = query,
                page = page)
        }

        filterAndOrder.contains(ORDER_BY_DESC_TITLE) ->{
            return searchFoldersOrderByTitleDESC(
                query = query,
                page = page)
        }

        filterAndOrder.contains(ORDER_BY_ASC_TITLE) ->{
            return searchFoldersOrderByTitleASC(
                query = query,
                page = page)
        }
        else ->
            return searchFoldersOrderByDateDESC(
                query = query,
                page = page
            )
    }
}












