package com.example.choplaygroundkotlin.framework.presentation.common

import android.content.SharedPreferences
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.choplaygroundkotlin.business.domain.model.FolderFactory
import com.example.choplaygroundkotlin.business.interactors.folderlist.FolderListInteractors
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
constructor(
    private val folderListInteractors: FolderListInteractors,
//    private val folderNetworkSyncManager: FolderNetworkSyncManager,
    private val folderFactory: FolderFactory,
    private val editor: SharedPreferences.Editor,
    private val sharedPreferences: SharedPreferences
) : ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when(modelClass){

            FolderListViewModel::class.java -> {
                FolderListViewModel(
                    folderInteractors = folderListInteractors,
                    folderFactory = folderFactory,
                    editor = editor,
                    sharedPreferences = sharedPreferences
                ) as T
            }

            else -> {
                throw IllegalArgumentException("unknown model class $modelClass")
            }
        }
    }
}