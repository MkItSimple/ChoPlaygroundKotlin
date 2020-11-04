package com.example.choplaygroundkotlin.business.interactors.folderlist

import com.example.choplaygroundkotlin.business.data.cache.CacheResponseHandler
import com.example.choplaygroundkotlin.business.data.cache.abstraction.FolderCacheDataSource
import com.example.choplaygroundkotlin.business.domain.state.*
import com.example.choplaygroundkotlin.business.data.util.safeCacheCall
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetNumFolders(
    private val folderCacheDataSource: FolderCacheDataSource
){

    fun getNumFolders(
        stateEvent: StateEvent
    ): Flow<DataState<FolderListViewState>?> = flow {

        val cacheResult = safeCacheCall(IO){
            folderCacheDataSource.getNumFolders()
        }
        val response =  object: CacheResponseHandler<FolderListViewState, Int>(
            response = cacheResult,
            stateEvent = stateEvent
        ){
            override suspend fun handleSuccess(resultObj: Int): DataState<FolderListViewState>? {
                val viewState = FolderListViewState(
                    numFoldersInCache = resultObj
                )
                return DataState.data(
                    response = Response(
                        message = GET_NUM_FOLDERS_SUCCESS,
                        uiComponentType = UIComponentType.None(),
                        messageType = MessageType.Success()
                    ),
                    data = viewState,
                    stateEvent = stateEvent
                )
            }
        }.getResult()

        emit(response)
    }

    companion object{
        val GET_NUM_FOLDERS_SUCCESS = "Successfully retrieved the number of folders from the cache."
        val GET_NUM_FOLDERS_FAILED = "Failed to get the number of folders from the cache."
    }
}