package com.example.choplaygroundkotlin.di

import com.example.choplaygroundkotlin.business.data.cache.abstraction.FolderCacheDataSource
import com.example.choplaygroundkotlin.business.data.cache.implementation.FolderCacheDataSourceImpl
import com.example.choplaygroundkotlin.business.data.network.abstraction.FolderNetworkDataSource
import com.example.choplaygroundkotlin.business.data.network.implementation.FolderNetworkDataSourceImpl
import com.example.choplaygroundkotlin.business.domain.model.FolderFactory
import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import com.example.choplaygroundkotlin.business.interactors.common.DeleteFolder
import com.example.choplaygroundkotlin.business.interactors.folderlist.*
import com.example.choplaygroundkotlin.framework.datasource.cache.abstraction.FolderDaoService
import com.example.choplaygroundkotlin.framework.datasource.cache.database.AppDatabase
import com.example.choplaygroundkotlin.framework.datasource.cache.database.FolderDao
import com.example.choplaygroundkotlin.framework.datasource.cache.implementation.FolderDaoServiceImpl
import com.example.choplaygroundkotlin.framework.datasource.cache.mappers.FolderCacheMapper
import com.example.choplaygroundkotlin.framework.datasource.network.abstraction.FolderFirestoreService
import com.example.choplaygroundkotlin.framework.datasource.network.implementation.FolderFirestoreServiceImpl
import com.example.choplaygroundkotlin.framework.datasource.network.mappers.FolderNetworkMapper
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Singleton

@ExperimentalCoroutinesApi
@FlowPreview
@Module
object AppModule {

    // https://developer.android.com/reference/java/text/SimpleDateFormat.html?hl=pt-br
    @JvmStatic
    @Singleton
    @Provides
    fun provideDateFormat(): SimpleDateFormat {
        val sdf = SimpleDateFormat("yyyy-MM-dd hh:mm:ss a", Locale.ENGLISH)
        sdf.timeZone = TimeZone.getTimeZone("UTC-7") // match firestore
        return sdf
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideDateUtil(dateFormat: SimpleDateFormat): DateUtil {
        return DateUtil(
            dateFormat
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFolderFactory(dateUtil: DateUtil): FolderFactory {
        return FolderFactory(
            dateUtil
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFirebaseAuth(): FirebaseAuth {
        return FirebaseAuth.getInstance()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFolderDAO(appDatabase: AppDatabase): FolderDao {
        return appDatabase.folderDao()
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFolderCacheMapper(dateUtil: DateUtil): FolderCacheMapper {
        return FolderCacheMapper(dateUtil)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFolderNetworkMapper(dateUtil: DateUtil): FolderNetworkMapper {
        return FolderNetworkMapper(dateUtil)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFolderDaoService(
        folderDao: FolderDao,
        folderEntityMapper: FolderCacheMapper,
        dateUtil: DateUtil
    ): FolderDaoService {
        return FolderDaoServiceImpl(folderDao, folderEntityMapper, dateUtil)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFolderCacheDataSource(
        folderDaoService: FolderDaoService
    ): FolderCacheDataSource {
        return FolderCacheDataSourceImpl(folderDaoService)
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFirestoreService(
        firebaseAuth: FirebaseAuth,
        firebaseFirestore: FirebaseFirestore,
        networkMapper: FolderNetworkMapper
    ): FolderFirestoreService {
        return FolderFirestoreServiceImpl(
            firebaseAuth,
            firebaseFirestore,
            networkMapper
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFolderNetworkDataSource(
        firestoreService: FolderFirestoreServiceImpl
    ): FolderNetworkDataSource {
        return FolderNetworkDataSourceImpl(
            firestoreService
        )
    }

    @JvmStatic
    @Singleton
    @Provides
    fun provideFolderListInteractors(
        folderCacheDataSource: FolderCacheDataSource,
        folderNetworkDataSource: FolderNetworkDataSource,
        folderFactory: FolderFactory
    ): FolderListInteractors {
        return FolderListInteractors(
            InsertNewFolder(folderCacheDataSource, folderNetworkDataSource, folderFactory),
            DeleteFolder(folderCacheDataSource, folderNetworkDataSource),
            SearchFolders(folderCacheDataSource),
            GetNumFolders(folderCacheDataSource),
            RestoreDeletedFolder(folderCacheDataSource, folderNetworkDataSource),
            DeleteMultipleFolders(folderCacheDataSource, folderNetworkDataSource),
            InsertMultipleFolders(folderCacheDataSource, folderNetworkDataSource)
        )
    }
}