package com.example.choplaygroundkotlin.business.interactors.folderlist

import com.example.choplaygroundkotlin.business.data.cache.abstraction.FolderCacheDataSource
import com.example.choplaygroundkotlin.business.data.network.abstraction.FolderNetworkDataSource
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.state.*
import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import com.example.choplaygroundkotlin.business.data.util.safeApiCall
import com.example.choplaygroundkotlin.business.data.util.safeCacheCall
import com.example.choplaygroundkotlin.framework.presentation.folderlist.state.FolderListViewState
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

// For testing
class InsertMultipleFolders(
    private val folderCacheDataSource: FolderCacheDataSource,
    private val folderNetworkDataSource: FolderNetworkDataSource
){

    fun insertFolders(
        numFolders: Int,
        stateEvent: StateEvent
    ): Flow<DataState<FolderListViewState>?> = flow {

        val folderList = FolderListTester.generateFolderList(numFolders)
        safeCacheCall(IO){
            folderCacheDataSource.insertFolders(folderList)
        }

        emit(
            DataState.data<FolderListViewState>(
                response = Response(
                    message = "success",
                    uiComponentType = UIComponentType.None(),
                    messageType = MessageType.None()
                ),
                data = null,
                stateEvent = stateEvent
            )
        )

        updateNetwork(folderList)
    }

    private suspend fun updateNetwork(folderList: List<Folder>){
        safeApiCall(IO){
            folderNetworkDataSource.insertOrUpdateFolders(folderList)
        }
    }

}


private object FolderListTester {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
    private val dateUtil =
        DateUtil(dateFormat)

    fun generateFolderList(numFolders: Int): List<Folder>{
        val list: ArrayList<Folder> = ArrayList()
        for(id in 0..numFolders){
            list.add(generateFolder())
        }
        return list
    }

    fun generateFolder(): Folder {
        val folder = Folder(
            id = UUID.randomUUID().toString(),
            folder_name = UUID.randomUUID().toString(),
            created_at = dateUtil.getCurrentTimestamp(),
            updated_at = dateUtil.getCurrentTimestamp()
        )
        return folder
    }
}