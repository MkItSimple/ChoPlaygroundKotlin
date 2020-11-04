package com.example.choplaygroundkotlin.business.interactors.folderlist

import com.example.choplaygroundkotlin.business.interactors.common.DeleteFolder
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListViewState


// Use cases
class FolderListInteractors (
    val insertNewFolder: InsertNewFolder,
    val deleteFolder: DeleteFolder<FolderListViewState>,
    val searchFolders: SearchFolders,
    val getNumFolders: GetNumFolders,
    val restoreDeletedFolder: RestoreDeletedFolder,
    val deleteMultipleFolders: DeleteMultipleFolders,
    val insertMultipleFolders: InsertMultipleFolders // for testing
)














