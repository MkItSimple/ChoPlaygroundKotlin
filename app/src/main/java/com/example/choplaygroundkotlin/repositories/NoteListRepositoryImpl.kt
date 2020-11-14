package com.example.choplaygroundkotlin.repositories

import androidx.lifecycle.LiveData
import com.example.choplaygroundkotlin.data.local.FolderDao
import com.example.choplaygroundkotlin.data.local.FolderItem
import com.example.choplaygroundkotlin.data.local.NoteDao
import com.example.choplaygroundkotlin.data.local.NoteItem
import javax.inject.Inject

class NoteListRepositoryImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val noteDao: NoteDao
) : NoteListRepository {

    override suspend fun insertNoteItem(noteItem: NoteItem) {
        noteDao.insertNoteItem(noteItem)
    }

    override fun observeAllNoteItems(): LiveData<List<NoteItem>> {
        return noteDao.observeAllNoteItems()
    }

    override suspend fun insertFolderItem(folderItem: FolderItem) {
        folderDao.insertFolderItem(folderItem)
    }

    override fun observeAllFolderItems(): LiveData<List<FolderItem>> {
        return folderDao.observeAllFolderItems()
    }
}