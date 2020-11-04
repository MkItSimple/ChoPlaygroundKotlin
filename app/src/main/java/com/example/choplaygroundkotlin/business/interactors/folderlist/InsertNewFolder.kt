package com.example.choplaygroundkotlin.business.interactors.folderlist

import com.example.choplaygroundkotlin.business.data.cache.CacheResponseHandler
import com.example.choplaygroundkotlin.business.domain.model.FolderFactory
import com.example.choplaygroundkotlin.business.domain.state.*
import com.example.choplaygroundkotlin.business.data.util.safeApiCall
import com.example.choplaygroundkotlin.business.data.util.safeCacheCall
import com.example.choplaygroundkotlin.business.data.cache.abstraction.FolderCacheDataSource
import com.example.choplaygroundkotlin.business.data.network.abstraction.FolderNetworkDataSource
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.util.*

class InsertNewFolder(
    private val folderCacheDataSource: FolderCacheDataSource,
    private val folderNetworkDataSource: FolderNetworkDataSource,
    private val folderFactory: FolderFactory
){

    fun insertNewFolder(
        id: String? = null,
        folder_name: String,
        stateEvent: StateEvent
    ): Flow<DataState<FolderListViewState>?> = flow {

        val newFolder = folderFactory.createSingleFolder(
            id = id ?: UUID.randomUUID().toString(),
            folder_name = folder_name
        )
        val cacheResult = safeCacheCall(IO){
            folderCacheDataSource.insertFolder(newFolder)
        }

        val cacheResponse = object: CacheResponseHandler<FolderListViewState, Long>(
            response = cacheResult,
            stateEvent = stateEvent
        ){
            override suspend fun handleSuccess(resultObj: Long): DataState<FolderListViewState>? {
                return if(resultObj > 0){
                    val viewState =
                        FolderListViewState(
                            newFolder = newFolder
                        )
                    DataState.data(
                        response = Response(
                            message = INSERT_FOLDER_SUCCESS,
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
                            message = INSERT_FOLDER_FAILED,
                            uiComponentType = UIComponentType.Toast(),
                            messageType = MessageType.Error()
                        ),
                        data = null,
                        stateEvent = stateEvent
                    )
                }
            }
        }.getResult()

        emit(cacheResponse)

        updateNetwork(cacheResponse?.stateMessage?.response?.message, newFolder)
    }

    private suspend fun updateNetwork(cacheResponse: String?, newFolder: Folder ){
        if(cacheResponse.equals(INSERT_FOLDER_SUCCESS)){

            safeApiCall(IO){
                folderNetworkDataSource.insertOrUpdateFolder(newFolder)
            }
        }
    }

    companion object{
        val INSERT_FOLDER_SUCCESS = "Successfully inserted new folder."
        val INSERT_FOLDER_FAILED = "Failed to insert new folder."
    }
}