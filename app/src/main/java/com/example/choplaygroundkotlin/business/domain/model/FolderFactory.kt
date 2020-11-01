package com.example.choplaygroundkotlin.business.domain.model

import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.collections.ArrayList

@Singleton
class FolderFactory
@Inject
constructor(
    private val dateUtil: DateUtil
) {

    fun createSingleFolder(
        id: String? = null,
        folder_name: String,
        notes_count: Int
    ): Folder {
        return Folder(
            id = id ?: UUID.randomUUID().toString(),
            folder_name = folder_name,
            notes_count = notes_count
        )
    }

//    fun createFolderList(numFolders: Int): List<Folder> {
//        val list: ArrayList<Folder> = ArrayList()
//        for(i in 0 until numFolders){ // exclusive on upper bound
//            list.add(
//                createSingleFolder(
//                    id = UUID.randomUUID().toString(),
//                    folder_name = UUID.randomUUID().toString(),
//                    notes_count = 0
//                )
//            )
//        }
//        return list
//    }


}
