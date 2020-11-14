package com.example.choplaygroundkotlin.notelist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.choplaygroundkotlin.data.local.FolderItem
import com.example.choplaygroundkotlin.data.local.NoteItem
import com.example.choplaygroundkotlin.repositories.NoteListRepository
import kotlinx.coroutines.launch

class NoteListViewModel @ViewModelInject constructor(
    private val repository: NoteListRepository
) : ViewModel() {

    val noteItems = repository.observeAllNoteItems()

    val fetchFoldersWithNotes = repository.fetchFoldersWithNotes()

    fun insertNoteItem(note: NoteItem) = viewModelScope.launch {
        repository.insertNoteItem(note)
    }

    val folderItems = repository.observeAllFolderItems()

    fun insertFolderItem(folder: FolderItem) = viewModelScope.launch {
        repository.insertFolderItem(folder)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAllFolders()
        repository.deleteAllNotes()
    }


}