package com.example.choplaygroundkotlin.data.network

import com.example.choplaygroundkotlin.domain.Note
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await
import javax.inject.Inject

class NoteFirestoreServiceImpl
@Inject
constructor(
    private val firestore: FirebaseFirestore,
    private val networkMapper: NetworkMapper
) : NoteFirestoreService {

    override suspend fun insertOrUpdateNote(note: Note) {
        val entity = networkMapper.mapToEntity(note)
        //entity.updated_at = Timestamp.now() // for updates
        firestore
            .collection(NOTES_COLLECTION)
            .document(USER_ID)
            .collection(FOLDERS_COLLECTION)
            .document(DEFAULT_FOLDER)
            .collection(NOTES_COLLECTION)
            .document(entity.id)
            .set(entity)
            .addOnFailureListener {
                // send error reports to Firebase Crashlytics
                //cLog(it.message)
            }
            .await()
    }

    companion object {
        const val FOLDERS_COLLECTION = "folders"

        const val NOTES_COLLECTION = "notes3"
        const val USERS_COLLECTION = "users"
        const val DELETES_COLLECTION = "deletes"
        const val USER_ID = "9E7fDYAUTNUPFirw4R28NhBZE1u1" // hardcoded for single user
        const val EMAIL = "mitch@tabian.ca"

        const val DEFAULT_FOLDER = "notes"
    }
}