package com.example.choplaygroundkotlin.framework.datasource.network.implementation

import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.framework.datasource.network.abstraction.FolderFirestoreService
import com.example.choplaygroundkotlin.framework.datasource.network.mappers.FolderNetworkMapper
import com.example.choplaygroundkotlin.framework.datasource.network.model.FolderNetworkEntity
import com.example.choplaygroundkotlin.util.cLog
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.*
import kotlinx.coroutines.tasks.await
import java.lang.Exception
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Firestore doc refs:
 * 1. add:  https://firebase.google.com/docs/firestore/manage-data/add-data
 * 2. delete: https://firebase.google.com/docs/firestore/manage-data/delete-data
 * 3. update: https://firebase.google.com/docs/firestore/manage-data/add-data#update-data
 * 4. query: https://firebase.google.com/docs/firestore/query-data/queries
 */
@Singleton
class FolderFirestoreServiceImpl
@Inject
constructor(
    private val firebaseAuth: FirebaseAuth, // might include auth in the future
    private val firestore: FirebaseFirestore,
    private val networkMapper: FolderNetworkMapper
): FolderFirestoreService {

    override suspend fun insertOrUpdateFolder(folder: Folder) {
        val entity = networkMapper.mapToEntity(folder)
        entity.updated_at = Timestamp.now() // for updates
        firestore
            .collection(FOLDERS_COLLECTION)
            .document(USER_ID)
            .collection(FOLDERS_COLLECTION)
            .document(entity.id)
            .set(entity)
            .addOnFailureListener {
                // send error reports to Firebase Crashlytics
                cLog(it.message)
            }
            .await()
    }

    override suspend fun deleteFolder(primaryKey: String) {
        firestore
            .collection(FOLDERS_COLLECTION)
            .document(USER_ID)
            .collection(FOLDERS_COLLECTION)
            .document(primaryKey)
            .delete()
            .addOnFailureListener {
                // send error reports to Firebase Crashlytics
                cLog(it.message)
            }
            .await()
    }

    override suspend fun insertDeletedFolder(folder: Folder) {
        val entity = networkMapper.mapToEntity(folder)
        firestore
            .collection(DELETES_COLLECTION)
            .document(USER_ID)
            .collection(FOLDERS_COLLECTION)
            .document(entity.id)
            .set(entity)
            .addOnFailureListener {
                // send error reports to Firebase Crashlytics
                cLog(it.message)
            }
            .await()
    }

    override suspend fun insertDeletedFolders(folders: List<Folder>) {
        if(folders.size > 500){
            throw Exception("Cannot delete more than 500 folders at a time in firestore.")
        }

        val collectionRef = firestore
            .collection(DELETES_COLLECTION)
            .document(USER_ID)
            .collection(FOLDERS_COLLECTION)

        firestore.runBatch { batch ->
            for(folder in folders){
                val documentRef = collectionRef.document(folder.id)
                batch.set(documentRef, networkMapper.mapToEntity(folder))
            }
        }.addOnFailureListener {
            // send error reports to Firebase Crashlytics
            cLog(it.message)
        }.await()
    }

    override suspend fun deleteDeletedFolder(folder: Folder) {
        val entity = networkMapper.mapToEntity(folder)
        firestore
            .collection(DELETES_COLLECTION)
            .document(USER_ID)
            .collection(FOLDERS_COLLECTION)
            .document(entity.id)
            .delete()
            .addOnFailureListener {
                // send error reports to Firebase Crashlytics
                cLog(it.message)
            }
            .await()
    }

    // used in testing
    override suspend fun deleteAllFolders() {
        firestore
            .collection(FOLDERS_COLLECTION)
            .document(USER_ID)
            .delete()
            .await()
        firestore
            .collection(DELETES_COLLECTION)
            .document(USER_ID)
            .delete()
            .await()
    }

    override suspend fun getDeletedFolders(): List<Folder> {
        return networkMapper.entityListToFolderList(
            firestore
                .collection(DELETES_COLLECTION)
                .document(USER_ID)
                .collection(FOLDERS_COLLECTION)
                .get()
                .addOnFailureListener {
                    // send error reports to Firebase Crashlytics
                    cLog(it.message)
                }
            .await().toObjects(FolderNetworkEntity::class.java)
        )
    }

    override suspend fun searchFolder(folder: Folder): Folder? {
        return firestore
            .collection(FOLDERS_COLLECTION)
            .document(USER_ID)
            .collection(FOLDERS_COLLECTION)
            .document(folder.id)
            .get()
            .addOnFailureListener {
                // send error reports to Firebase Crashlytics
                cLog(it.message)
            }
            .await()
            .toObject(FolderNetworkEntity::class.java)?.let {
                networkMapper.mapFromEntity(it)
            }
    }

    override suspend fun getAllFolders(): List<Folder> {
        return networkMapper.entityListToFolderList(
            firestore
                .collection(FOLDERS_COLLECTION)
                .document(USER_ID)
                .collection(FOLDERS_COLLECTION)
                .get()
                .addOnFailureListener {
                    // send error reports to Firebase Crashlytics
                    cLog(it.message)
                }
                .await()
                .toObjects(FolderNetworkEntity::class.java)
        )
    }

    override suspend fun insertOrUpdateFolders(folders: List<Folder>) {

        if(folders.size > 500){
            throw Exception("Cannot insert more than 500 folders at a time into firestore.")
        }

        val collectionRef = firestore
            .collection(FOLDERS_COLLECTION)
            .document(USER_ID)
            .collection(FOLDERS_COLLECTION)

        firestore.runBatch { batch ->
            for(folder in folders){
                val entity = networkMapper.mapToEntity(folder)
                entity.updated_at = Timestamp.now()
                val documentRef = collectionRef.document(folder.id)
                batch.set(documentRef, entity)
            }
        }.addOnFailureListener {
            // send error reports to Firebase Crashlytics
            cLog(it.message)
        }.await()

    }

    companion object {
        const val FOLDERS_COLLECTION = "folders"
        const val USERS_COLLECTION = "users"
        const val DELETES_COLLECTION = "deletes"
        const val USER_ID = "9E7fDYAUTNUPFirw4R28NhBZE1u1" // hardcoded for single user
        const val EMAIL = "mitch@tabian.ca"
    }
}












