package com.example.choplaygroundkotlin.repositories

import androidx.lifecycle.LiveData
import com.example.choplaygroundkotlin.data.local.*
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

    override suspend fun deleteAllNotes() {
        return noteDao.deleteAllNotes()
    }

    override suspend fun insertFolderItem(folderItem: FolderItem) {
        folderDao.insertFolderItem(folderItem)
    }

    override fun observeAllFolderItems(): LiveData<List<FolderItem>> {
        return folderDao.observeAllFolderItems()
    }

    override suspend fun deleteAllFolders() {
        return folderDao.deleteAllFolders()
    }

    override fun fetchFoldersWithNotes(): LiveData<List<FolderWithNotes>> {
        return folderDao.fetchFoldersWithNotes()
    }
}