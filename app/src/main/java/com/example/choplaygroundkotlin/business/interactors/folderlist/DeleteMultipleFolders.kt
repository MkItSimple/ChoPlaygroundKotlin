package com.example.choplaygroundkotlin.business.interactors.folderlist

import com.example.choplaygroundkotlin.business.data.cache.CacheResponseHandler
import com.example.choplaygroundkotlin.business.data.cache.abstraction.FolderCacheDataSource
import com.example.choplaygroundkotlin.business.data.network.abstraction.FolderNetworkDataSource
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.state.*
import com.example.choplaygroundkotlin.business.data.util.safeApiCall
import com.example.choplaygroundkotlin.business.data.util.safeCacheCall
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class DeleteMultipleFolders(
    private val folderCacheDataSource: FolderCacheDataSource,
    private val folderNetworkDataSource: FolderNetworkDataSource
){

    // set true if an error occurs when deleting any of the folders from cache
    private var onDeleteError: Boolean = false

    /**
     * Logic:
     * 1. execute all the deletes and save result into an ArrayList<DataState<FolderListViewState>>
     * 2a. If one of the results is a failure, emit an "error" response
     * 2b. If all success, emit success response
     * 3. Update network with folders that were successfully deleted
     */
    fun deleteFolders(
        folders: List<Folder>,
        stateEvent: StateEvent
    ): Flow<DataState<FolderListViewState>?> = flow {

        val successfulDeletes: ArrayList<Folder> = ArrayList() // folders that were successfully deleted
        for(folder in folders){
            val cacheResult = safeCacheCall(IO){
                folderCacheDataSource.deleteFolder(folder.id)
            }

            val response = object: CacheResponseHandler<FolderListViewState, Int>(
                response = cacheResult,
                stateEvent = stateEvent
            ){
                override suspend fun handleSuccess(resultObj: Int): DataState<FolderListViewState>? {
                    if(resultObj < 0){ // if error
                        onDeleteError = true
                    }
                    else{
                        successfulDeletes.add(folder)
                    }
                    return null
                }
            }.getResult()

            // check for random errors
            if(response?.stateMessage?.response?.message
                    ?.contains(stateEvent.errorInfo()) == true){
                onDeleteError = true
            }

        }

        if(onDeleteError){
            emit(
                DataState.data<FolderListViewState>(
                    response = Response(
                        message = DELETE_NOTES_ERRORS,
                        uiComponentType = UIComponentType.Dialog(),
                        messageType = MessageType.Success()
                    ),
                    data = null,
                    stateEvent = stateEvent
                )
            )
        }
        else{
            emit(
                DataState.data<FolderListViewState>(
                    response = Response(
                        message = DELETE_NOTES_SUCCESS,
                        uiComponentType = UIComponentType.Toast(),
                        messageType = MessageType.Success()
                    ),
                    data = null,
                    stateEvent = stateEvent
                )
            )
        }

        updateNetwork(successfulDeletes)
    }

    private suspend fun updateNetwork(successfulDeletes: ArrayList<Folder>){
        for (folder in successfulDeletes){

            // delete from "folders" node
            safeApiCall(IO){
                folderNetworkDataSource.deleteFolder(folder.id)
            }

            // insert into "deletes" node
            safeApiCall(IO){
                folderNetworkDataSource.insertDeletedFolder(folder)
            }
        }
    }

    companion object{
        val DELETE_NOTES_SUCCESS = "Successfully deleted folders."
        val DELETE_NOTES_ERRORS = "Not all the folders you selected were deleted. There was some errors."
        val DELETE_NOTES_YOU_MUST_SELECT = "You haven't selected any folders to delete."
        val DELETE_NOTES_ARE_YOU_SURE = "Are you sure you want to delete these?"
    }
}













