package com.example.choplaygroundkotlin.business.interactors.splash

import com.example.choplaygroundkotlin.business.data.cache.CacheResponseHandler
import com.example.choplaygroundkotlin.business.data.cache.abstraction.FolderCacheDataSource
import com.example.choplaygroundkotlin.business.data.network.ApiResponseHandler
import com.example.choplaygroundkotlin.business.data.network.abstraction.FolderNetworkDataSource
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.state.DataState
import com.example.choplaygroundkotlin.business.data.util.safeApiCall
import com.example.choplaygroundkotlin.business.data.util.safeCacheCall
import com.example.choplaygroundkotlin.util.printLogD
import kotlinx.coroutines.Dispatchers.IO

/*
    Search firestore for all notes in the "deleted" node.
    It will then search the cache for notes matching those deleted notes.
    If a match is found, it is deleted from the cache.
 */
class SyncDeletedFolders(
    private val noteCacheDataSource: FolderCacheDataSource,
    private val noteNetworkDataSource: FolderNetworkDataSource
){

    suspend fun syncDeletedFolders(){

        val apiResult = safeApiCall(IO){
            noteNetworkDataSource.getDeletedFolders()
        }
        val response = object: ApiResponseHandler<List<Folder>, List<Folder>>(
            response = apiResult,
            stateEvent = null
        ){
            override suspend fun handleSuccess(resultObj: List<Folder>): DataState<List<Folder>>? {
                printLogD("syncDeletedFolders", "deleted notes: ${resultObj.size}")
                return DataState.data(
                    response = null,
                    data = resultObj,
                    stateEvent = null
                )
            }
        }

        val notes = response.getResult()?.data?: ArrayList()
        printLogD("syncDeletedFolders", "notes: ${notes.size}")

        val cacheResult = safeCacheCall(IO){
            noteCacheDataSource.deleteFolders(notes)
        }

        object: CacheResponseHandler<Int, Int>(
            response = cacheResult,
            stateEvent = null
        ){
            override suspend fun handleSuccess(resultObj: Int): DataState<Int>? {
                printLogD("syncDeletedFolders",
                    "num deleted notes: ${resultObj}")
                return DataState.data(
                    response = null,
                    data = resultObj,
                    stateEvent = null
                )
            }
        }.getResult()

    }


}
























