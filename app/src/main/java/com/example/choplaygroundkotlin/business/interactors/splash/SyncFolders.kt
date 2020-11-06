package com.example.choplaygroundkotlin.business.interactors.splash

import com.example.choplaygroundkotlin.business.data.cache.CacheResponseHandler
import com.example.choplaygroundkotlin.business.data.cache.abstraction.FolderCacheDataSource
import com.example.choplaygroundkotlin.business.data.network.ApiResponseHandler
import com.example.choplaygroundkotlin.business.data.network.abstraction.FolderNetworkDataSource
import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.state.DataState
import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import com.example.choplaygroundkotlin.business.data.util.safeApiCall
import com.example.choplaygroundkotlin.business.data.util.safeCacheCall
import com.example.choplaygroundkotlin.util.printLogD
import com.google.firebase.Timestamp
import kotlinx.coroutines.*
import kotlinx.coroutines.Dispatchers.IO

/*
    Query all folders in the cache. It will then search firestore for
    each corresponding folder but with an extra filter: It will only return folders where
    cached_folder.updated_at < network_folder.updated_at. It will update the cached folders
    where that condition is met. If the folder does not exist in Firestore (maybe due to
    network being down at time of insertion), insert it
    (**This must be done AFTER
    checking for deleted folders and performing that sync**).
 */
@Suppress("IMPLICIT_CAST_TO_ANY")
class SyncFolders(
    private val folderCacheDataSource: FolderCacheDataSource,
    private val folderNetworkDataSource: FolderNetworkDataSource,
    private val dateUtil: DateUtil
){

    suspend fun syncFolders() {

        val cachedFoldersList = getCachedFolders()

        val networkFoldersList = getNetworkFolders()

        syncNetworkFoldersWithCachedFolders(
            ArrayList(cachedFoldersList),
            networkFoldersList
        )
    }

    private suspend fun getCachedFolders(): List<Folder> {
        val cacheResult = safeCacheCall(IO){
            folderCacheDataSource.getAllFolders()
        }

        val response = object: CacheResponseHandler<List<Folder>, List<Folder>>(
            response = cacheResult,
            stateEvent = null
        ){
            override suspend fun handleSuccess(resultObj: List<Folder>): DataState<List<Folder>>? {
                return DataState.data(
                    response = null,
                    data = resultObj,
                    stateEvent = null
                )
            }

        }.getResult()

        return response?.data ?: ArrayList()
    }

    private suspend fun getNetworkFolders(): List<Folder>{
        val networkResult = safeApiCall(IO){
            folderNetworkDataSource.getAllFolders()
        }

        val response = object: ApiResponseHandler<List<Folder>, List<Folder>>(
            response = networkResult,
            stateEvent = null
        ){
            override suspend fun handleSuccess(resultObj: List<Folder>): DataState<List<Folder>>? {
                return DataState.data(
                    response = null,
                    data = resultObj,
                    stateEvent = null
                )
            }
        }.getResult()

        return response?.data ?: ArrayList()
    }

    // get all folders from network
    // if they do not exist in cache, insert them
    // if they do exist in cache, make sure they are up to date
    // while looping, remove folders from the cachedFolders list. If any remain, it means they
    // should be in the network but aren't. So insert them.
    private suspend fun syncNetworkFoldersWithCachedFolders(
        cachedFolders: ArrayList<Folder>,
        networkFolders: List<Folder>
    ) = withContext(IO){

        for(folder in networkFolders){
            folderCacheDataSource.searchFolderById(folder.id)?.let { cachedFolder ->
                cachedFolders.remove(cachedFolder)
                checkIfCachedFolderRequiresUpdate(cachedFolder, folder)
            }?: folderCacheDataSource.insertFolder(folder)
        }
        // insert remaining into network
        for(cachedFolder in cachedFolders){
            folderNetworkDataSource.insertOrUpdateFolder(cachedFolder)
        }
    }

    private suspend fun checkIfCachedFolderRequiresUpdate(
        cachedFolder: Folder,
        networkFolder: Folder
    ){
        val cacheUpdatedAt = dateUtil.convertStringDateToFirebaseTimestamp(cachedFolder.updated_at)
        val networkUpdatedAt = dateUtil.convertStringDateToFirebaseTimestamp(networkFolder.updated_at)

        // update cache (network has newest data)
        if(networkUpdatedAt > cacheUpdatedAt){
            printLogD("SyncFolders",
                "cacheUpdatedAt: ${cacheUpdatedAt}, " +
                        "networkUpdatedAt: ${networkUpdatedAt}, " +
                        "folder: ${cachedFolder.folder_name}")
            safeCacheCall(IO){
                folderCacheDataSource.updateFolder(
                    networkFolder.id,
                    networkFolder.folder_name,
                    networkFolder.updated_at // retain network timestamp
                )
            }
        }
        // update network (cache has newest data)
        else if(networkUpdatedAt < cacheUpdatedAt){
            safeApiCall(IO){
                folderNetworkDataSource.insertOrUpdateFolder(cachedFolder)
            }
        }
    }

    // for debugging
//    private fun printCacheLongTimestamps(folders: List<Folder>){
//        for(folder in folders){
//            printLogD("SyncFolders",
//                "date: ${dateUtil.convertServerStringDateToLong(folder.updated_at)}")
//        }
//    }

}






























