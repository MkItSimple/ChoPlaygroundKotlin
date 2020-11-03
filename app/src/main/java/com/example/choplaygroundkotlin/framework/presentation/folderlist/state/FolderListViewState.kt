package com.example.choplaygroundkotlin.framework.presentation.folderlist.state

import android.os.Parcelable
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.state.ViewState
import kotlinx.android.parcel.Parcelize

@Parcelize
data class FolderListViewState(

    var folder: Folder? = null,
    var isUpdatePending: Boolean? = null,

    var folderList: ArrayList<Folder>? = null,
    var newFolder: Folder? = null, // folder that can be created with fab
    var folderPendingDelete: FolderPendingDelete? = null, // set when delete is pending (can be undone)
    var searchQuery: String? = null,
    var page: Int? = null,
    var isQueryExhausted: Boolean? = null,
    var filter: String? = null,
    var order: String? = null,
    var layoutManagerState: Parcelable? = null,
    var numFoldersInCache: Int? = null

) : Parcelable, ViewState {

    @Parcelize
    data class FolderPendingDelete(
        var folder: Folder? = null,
        var listPosition: Int? = null
    ) : Parcelable
}