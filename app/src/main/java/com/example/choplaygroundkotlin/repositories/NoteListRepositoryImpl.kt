package com.example.choplaygroundkotlin.repositories

import androidx.lifecycle.LiveData
import com.example.choplaygroundkotlin.data.local.*
import com.example.choplaygroundkotlin.domain.Note
import javax.inject.Inject

class NoteListRepositoryImpl @Inject constructor(
    private val folderDao: FolderDao,
    private val noteDao: NoteDao,
    private val noteMapper: CacheMapper
) : NoteListRepository {

    override suspend fun insertNote(note: Note) : Long {
        return noteDao.insertNote(noteMapper.mapToEntity(note))
    }

    override fun observeAllNoteItems(): LiveData<List<NoteCacheEntity>> {
        return noteDao.observeAllNoteItems()
    }

    override suspend fun deleteAllNotes() {
        return noteDao.deleteAllNotes()
    }

    override suspend fun insertFolderItem(folderCacheEntity: FolderCacheEntity) {
        folderDao.insertFolderItem(folderCacheEntity)
    }

    override fun observeAllFolderItems(): LiveData<List<FolderCacheEntity>> {
        return folderDao.observeAllFolderItems()
    }

    override suspend fun deleteAllFolders() {
        return folderDao.deleteAllFolders()
    }

    override fun fetchFoldersWithNotes(): LiveData<List<FolderWithNotes>> {
        return folderDao.fetchFoldersWithNotes()
    }
}