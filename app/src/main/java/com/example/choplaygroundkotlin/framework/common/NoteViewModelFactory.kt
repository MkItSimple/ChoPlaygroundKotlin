package com.example.choplaygroundkotlin.framework.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.choplaygroundkotlin.framework.presentation.folderlist.FolderListViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import javax.inject.Singleton

@FlowPreview
@ExperimentalCoroutinesApi
@Singleton
class NoteViewModelFactory
@Inject
constructor() : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass){

            FolderListViewModel::class.java -> {
                FolderListViewModel() as T
            }

//            NoteListViewModel::class.java -> {
//                NoteListViewModel(
//                    noteInteractors = noteListInteractors,
//                    noteFactory = noteFactory,
//                    editor = editor,
//                    sharedPreferences = sharedPreferences
//                ) as T
//            }
//
//            NoteDetailViewModel::class.java -> {
//                NoteDetailViewModel(
//                    noteInteractors = noteDetailInteractors
//                ) as T
//            }
//
//            SplashViewModel::class.java -> {
//                SplashViewModel(
//                    noteNetworkSyncManager = noteNetworkSyncManager
//                ) as T
//            }

            else -> {
                throw IllegalArgumentException("unknown model class $modelClass")
            }
        }
    }
}