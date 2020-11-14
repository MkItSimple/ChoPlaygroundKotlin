package com.example.choplaygroundkotlin.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FolderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolderItem(folderCacheEntity: FolderCacheEntity)

    @Query("SELECT * FROM folders")
    fun observeAllFolderItems(): LiveData<List<FolderCacheEntity>>

    @Query("DELETE FROM folders")
    suspend fun deleteAllFolders()

    // Folders and Notes
    @Query("SELECT * FROM folders")
    @Transaction
    fun fetchFoldersWithNotes(): LiveData<List<FolderWithNotes>>
}