package com.example.choplaygroundkotlin.di

import android.content.Context
import androidx.room.Room
import com.example.choplaygroundkotlin.data.local.CacheMapper
import com.example.choplaygroundkotlin.data.local.FolderDao
import com.example.choplaygroundkotlin.data.local.NoteDao
import com.example.choplaygroundkotlin.data.local.NoteDatabase
import com.example.choplaygroundkotlin.data.network.NetworkMapper
import com.example.choplaygroundkotlin.data.network.NoteFirestoreService
import com.example.choplaygroundkotlin.data.network.NoteFirestoreServiceImpl
import com.example.choplaygroundkotlin.other.Constants.DATABASE_NAME
import com.example.choplaygroundkotlin.repositories.NoteListRepository
import com.example.choplaygroundkotlin.repositories.NoteListRepositoryImpl
import com.google.firebase.firestore.FirebaseFirestore
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
        folderDao: FolderDao,
        noteDao: NoteDao,
        cacheMapper: CacheMapper
    ) = NoteListRepositoryImpl(folderDao, noteDao, cacheMapper) as NoteListRepository

    @Singleton
    @Provides
    fun provideFolderDao(
        database: NoteDatabase
    ) = database.folderDao()

    @Singleton
    @Provides
    fun provideNoteDao(
        database: NoteDatabase
    ) = database.noteDao()

    @Singleton
    @Provides
    fun provideFirebaseFirestore(): FirebaseFirestore {
        return FirebaseFirestore.getInstance()
    }

    @Singleton
    @Provides
    fun provideFirestoreService(
        firebaseFirestore: FirebaseFirestore,
        networkMapper: NetworkMapper
    ): NoteFirestoreService {
        return NoteFirestoreServiceImpl(
            firebaseFirestore,
            networkMapper
        )
    }

}