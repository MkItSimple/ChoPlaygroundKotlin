package com.example.choplaygroundkotlin.notelist

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.choplaygroundkotlin.data.local.NoteItem
import com.example.choplaygroundkotlin.other.Event
import com.example.choplaygroundkotlin.other.Resource
import com.example.choplaygroundkotlin.repositories.NoteListRepository
import kotlinx.coroutines.launch

class NoteListViewModel @ViewModelInject constructor(
    private val repository: NoteListRepository
) : ViewModel() {

    val noteItems = repository.observeAllNoteItems()

//    private val _insertNoteItemStatus = MutableLiveData<Event<Resource<NoteItem>>>()
//    val insertNoteItemStatus: LiveData<Event<Resource<NoteItem>>> = _insertNoteItemStatus

    fun insertNoteItem(note: NoteItem) = viewModelScope.launch {
        repository.insertNoteItem(note)
    }

    

}