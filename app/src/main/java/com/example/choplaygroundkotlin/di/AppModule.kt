package com.example.choplaygroundkotlin.di

import android.content.Context
import androidx.room.Room
import com.example.choplaygroundkotlin.data.local.NoteDao
import com.example.choplaygroundkotlin.data.local.NoteDatabase
import com.example.choplaygroundkotlin.other.Constants.DATABASE_NAME
import com.example.choplaygroundkotlin.repositories.NoteListRepository
import com.example.choplaygroundkotlin.repositories.NoteListRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ApplicationComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Singleton

@Module
@InstallIn(ApplicationComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideNoteDatabase(
        @ApplicationContext context: Context
    ) = Room.databaseBuilder(context, NoteDatabase::class.java, DATABASE_NAME).build()

    @Singleton
    @Provides
    fun provideDefaultNoteListRepository(
        dao: NoteDao
    ) = NoteListRepositoryImpl(dao) as NoteListRepository

    @Singleton
    @Provides
    fun provideNoteDao(
        database: NoteDatabase
    ) = database.noteDao()
}