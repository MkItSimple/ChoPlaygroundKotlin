package com.example.choplaygroundkotlin.data.network

import com.example.choplaygroundkotlin.domain.Note

interface NoteFirestoreService {

    suspend fun insertOrUpdateNote(note: Note)
}