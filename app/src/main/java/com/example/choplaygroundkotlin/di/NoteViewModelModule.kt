package com.example.choplaygroundkotlin.di

import android.content.SharedPreferences
import androidx.lifecycle.ViewModelProvider
import com.example.choplaygroundkotlin.business.domain.model.FolderFactory
import com.example.choplaygroundkotlin.business.interactors.folderlist.FolderListInteractors
import com.example.choplaygroundkotlin.framework.presentation.common.NoteViewModelFactory
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Module
object NoteViewModelModule {

    @Singleton
    @JvmStatic
    @Provides
    fun provideNoteViewModelFactory(
        folderListInteractors: FolderListInteractors,
//        folderDetailInteractors: FolderDetailInteractors,
//        folderNetworkSyncManager: FolderNetworkSyncManager,
        folderFactory: FolderFactory,
        editor: SharedPreferences.Editor,
        sharedPreferences: SharedPreferences
    ): ViewModelProvider.Factory{
        return NoteViewModelFactory(
            folderListInteractors = folderListInteractors,
//            folderDetailInteractors = folderDetailInteractors,
//            folderNetworkSyncManager = folderNetworkSyncManager,
            folderFactory = folderFactory,
            editor = editor,
            sharedPreferences = sharedPreferences
        )
    }

}