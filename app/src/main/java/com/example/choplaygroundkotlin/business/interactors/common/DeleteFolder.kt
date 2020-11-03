package com.example.choplaygroundkotlin.business.interactors.common

import com.example.choplaygroundkotlin.business.data.cache.CacheResponseHandler
import com.example.choplaygroundkotlin.business.data.cache.abstraction.FolderCacheDataSource
import com.example.choplaygroundkotlin.business.data.network.abstraction.FolderNetworkDataSource
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.state.*
import com.example.choplaygroundkotlin.business.data.util.safeApiCall
import com.example.choplaygroundkotlin.business.data.util.safeCacheCall
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteFolder<ViewState>(
    private val folderCacheDataSource: FolderCacheDataSource,
    private val folderNetworkDataSource: FolderNetworkDataSource
){

    fun deleteFolder(
        folder: Folder,
        stateEvent: StateEvent
    ): Flow<DataState<ViewState>?> = flow {

        val cacheResult = safeCacheCall(IO){
            folderCacheDataSource.deleteFolder(folder.id)
        }

        val response = object: CacheResponseHandler<ViewState, Int>(
            response = cacheResult,
            stateEvent = stateEvent
        ){
            override suspend fun handleSuccess(resultObj: Int): DataState<ViewState>? {
                return if(resultObj > 0){
                    DataState.data(
                        response = Response(
                            message = DELETE_NOTE_SUCCESS,
                            uiComponentType = UIComponentType.None(),
                            messageType = MessageType.Success()
                        ),
                        data = null,
                        stateEvent = stateEvent
                    )
                }
                else{
                    DataState.data(
                        response = Response(
                            message = DELETE_NOTE_FAILED,
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

        // update network
        if(response?.stateMessage?.response?.message.equals(DELETE_NOTE_SUCCESS)){

            // delete from 'folders' node
            safeApiCall(IO){
                folderNetworkDataSource.deleteFolder(folder.id)
            }

            // insert into 'deletes' node
            safeApiCall(IO){
                folderNetworkDataSource.insertDeletedFolder(folder)
            }

        }
    }

    companion object{
        val DELETE_NOTE_SUCCESS = "Successfully deleted folder."
        val DELETE_NOTE_PENDING = "Delete pending..."
        val DELETE_NOTE_FAILED = "Failed to delete folder."
        val DELETE_ARE_YOU_SURE = "Are you sure you want to delete this?"
    }
}













