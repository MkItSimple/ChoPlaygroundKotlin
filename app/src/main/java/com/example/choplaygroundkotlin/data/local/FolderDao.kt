package com.example.choplaygroundkotlin.data.local

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface FolderDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFolderItem(folderItem: FolderItem)

    @Query("SELECT * FROM folders")
    fun observeAllFolderItems(): LiveData<List<FolderItem>>
}