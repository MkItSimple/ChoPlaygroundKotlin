package com.example.choplaygroundkotlin.framework.presentation.folderlist

import androidx.lifecycle.LiveData
import com.example.choplaygroundkotlin.business.domain.state.StateEvent
import com.example.choplaygroundkotlin.framework.presentation.common.BaseViewModel
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListInteractionManager
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListToolbarState
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListViewState
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import javax.inject.Inject
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Singleton
class FolderListViewModel
@Inject
constructor(): BaseViewModel<FolderListViewState>() {

    val folderListInteractionManager =
        FolderListInteractionManager()

    val toolbarState: LiveData<FolderListToolbarState>
        get() = folderListInteractionManager.toolbarState
    
    override fun setStateEvent(stateEvent: StateEvent) {
        TODO("Not yet implemented")
    }

    fun setToolbarState(state: FolderListToolbarState)
            = folderListInteractionManager.setToolbarState(state)

    fun isMultiSelectionStateActive()
            = folderListInteractionManager.isMultiSelectionStateActive()
}