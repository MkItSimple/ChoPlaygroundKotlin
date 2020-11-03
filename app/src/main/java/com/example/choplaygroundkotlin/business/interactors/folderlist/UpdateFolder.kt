package com.example.choplaygroundkotlin.business.interactors.folderdetail

import com.example.choplaygroundkotlin.business.data.cache.CacheResponseHandler
import com.example.choplaygroundkotlin.business.data.cache.abstraction.FolderCacheDataSource
import com.example.choplaygroundkotlin.business.data.network.abstraction.FolderNetworkDataSource
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.state.*
import com.example.choplaygroundkotlin.business.data.util.safeApiCall
import com.example.choplaygroundkotlin.business.data.util.safeCacheCall
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListViewState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class UpdateFolder(
    private val folderCacheDataSource: FolderCacheDataSource,
    private val folderNetworkDataSource: FolderNetworkDataSource
){

    fun updateFolder(
        folder: Folder,
        stateEvent: StateEvent
    ): Flow<DataState<FolderListViewState>?> = flow {

        val cacheResult = safeCacheCall(Dispatchers.IO){
            folderCacheDataSource.updateFolder(
                primaryKey = folder.id,
                newFolderName = folder.folder_name,
                newNotesCount = folder.notes_count,
                timestamp = null // generate new timestamp
            )
        }

        val response = object: CacheResponseHandler<FolderListViewState, Int>(
            response = cacheResult,
            stateEvent = stateEvent
        ){
            override suspend fun handleSuccess(resultObj: Int): DataState<FolderListViewState>? {
                return if(resultObj > 0){
                    DataState.data(
                        response = Response(
                            message = UPDATE_FOLDER_SUCCESS,
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Success()
                        ),
                        data = null,
                        stateEvent = stateEvent
                    )
                }
                else{
                    DataState.data(
                        response = Response(
                            message = UPDATE_FOLDER_FAILED,
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Error()
                        ),
                        data = null,
                        stateEvent = stateEvent
                    )
                }
            }
        }.getResult()

        emit(response)

        updateNetwork(response?.stateMessage?.response?.message, folder)
    }

    private suspend fun updateNetwork(response: String?, folder: Folder) {
        if(response.equals(UPDATE_FOLDER_SUCCESS)){

            safeApiCall(Dispatchers.IO){
                folderNetworkDataSource.insertOrUpdateFolder(folder)
            }
        }
    }

    companion object{
        val UPDATE_FOLDER_SUCCESS = "Successfully updated folder."
        val UPDATE_FOLDER_FAILED = "Failed to update folder."
        val UPDATE_FOLDER_FAILED_PK = "Update failed. Folder is missing primary key."

    }
}