package com.example.choplaygroundkotlin.notelist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.choplaygroundkotlin.data.local.FolderCacheEntity
import com.example.choplaygroundkotlin.data.local.NoteCacheEntity
import com.example.choplaygroundkotlin.data.network.NoteFirestoreService
import com.example.choplaygroundkotlin.domain.Note
import com.example.choplaygroundkotlin.repositories.NoteListRepository
import kotlinx.coroutines.launch

class NoteListViewModel @ViewModelInject constructor(
    private val repository: NoteListRepository,
    private val firestoreService: NoteFirestoreService
) : ViewModel() {

    val noteItems = repository.observeAllNoteItems()

    val fetchFoldersWithNotes = repository.fetchFoldersWithNotes()

    fun insertNoteItem(note: Note) = viewModelScope.launch {
        repository.insertNote(note)
        firestoreService.insertOrUpdateNote(note)
    }

    val folderItems = repository.observeAllFolderItems()

    fun insertFolderItem(folder: FolderCacheEntity) = viewModelScope.launch {
        repository.insertFolderItem(folder)
    }

    fun deleteAllNotes() = viewModelScope.launch {
        repository.deleteAllNotes()
    }

    fun deleteAllFolders() = viewModelScope.launch {
        repository.deleteAllFolders()
    }

}