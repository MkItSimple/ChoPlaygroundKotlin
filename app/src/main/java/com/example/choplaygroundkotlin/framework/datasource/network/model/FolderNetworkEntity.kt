package com.example.choplaygroundkotlin.framework.datasource.network.model

import com.google.firebase.Timestamp


data class FolderNetworkEntity(

    var id: String,

    var folder_name: String,

    var notes_count: Int,

    var updated_at: Timestamp,

    var created_at: Timestamp

){

    // no arg constructor for firestore
    constructor(): this(
        "",
        "",
        0,
        Timestamp.now(),
        Timestamp.now()
    )



    companion object{

        const val UPDATED_AT_FIELD = "updated_at"
        const val TITLE_FIELD = "folder_name"
        const val NOTES_COUNT_FIELD = "notes_count"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FolderNetworkEntity

        if (id != other.id) return false
        if (folder_name != other.folder_name) return false
        if (notes_count != other.notes_count) return false
//        if (updated_at != other.updated_at) return false // ignore
        if (created_at != other.created_at) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + folder_name.hashCode()
        result = 31 * result + notes_count.hashCode()
        result = 31 * result + updated_at.hashCode()
        result = 31 * result + created_at.hashCode()
        return result
    }
}









