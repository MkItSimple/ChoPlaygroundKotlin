package com.example.choplaygroundkotlin.business.interactors.folderlist

import com.example.choplaygroundkotlin.business.data.cache.CacheResponseHandler
import com.example.choplaygroundkotlin.business.data.cache.abstraction.FolderCacheDataSource
import com.example.choplaygroundkotlin.business.data.network.abstraction.FolderNetworkDataSource
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.state.*
import com.example.choplaygroundkotlin.business.data.util.safeApiCall
import com.example.choplaygroundkotlin.business.data.util.safeCacheCall
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListViewState
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListViewState.*
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class RestoreDeletedFolder(
    private val folderCacheDataSource: FolderCacheDataSource,
    private val folderNetworkDataSource: FolderNetworkDataSource
){

    fun restoreDeletedFolder(
        folder: Folder,
        stateEvent: StateEvent
    ): Flow<DataState<FolderListViewState>?> = flow {

        val cacheResult = safeCacheCall(IO){
            folderCacheDataSource.insertFolder(folder)
        }

        val response = object: CacheResponseHandler<FolderListViewState, Long>(
            response = cacheResult,
            stateEvent = stateEvent
        ){
            override suspend fun handleSuccess(resultObj: Long): DataState<FolderListViewState>? {
                return if(resultObj > 0){
                    val viewState =
                        FolderListViewState(
                            folderPendingDelete = FolderPendingDelete(
                                folder = folder
                            )
                        )
                    DataState.data(
                        response = Response(
                            message = RESTORE_NOTE_SUCCESS,
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Success()
                        ),
                        data = viewState,
                        stateEvent = stateEvent
                    )
                }
                else{
                    DataState.data(
                        response = Response(
                            message = RESTORE_NOTE_FAILED,
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
        if(response.equals(RESTORE_NOTE_SUCCESS)){

            // insert into "folders" node
            safeApiCall(IO){
                folderNetworkDataSource.insertOrUpdateFolder(folder)
            }

            // remove from "deleted" node
            safeApiCall(IO){
                folderNetworkDataSource.deleteDeletedFolder(folder)
            }
        }
    }

    companion object{

        val RESTORE_NOTE_SUCCESS = "Successfully restored the deleted folder."
        val RESTORE_NOTE_FAILED = "Failed to restore the deleted folder."

    }
}













