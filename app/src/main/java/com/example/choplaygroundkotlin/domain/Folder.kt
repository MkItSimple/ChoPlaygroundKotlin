package com.example.choplaygroundkotlin.domain

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Folder(
    val folder_id: String,
    val folder_name: String,
    val notes_count: String,
    val updated_at: String,
    val created_at: String
) : Parcelable {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Folder

        if (folder_id != other.folder_id) return false
        if (folder_name != other.folder_name) return false
        if (notes_count != other.notes_count) return false

        return true
    }

    override fun hashCode(): Int {
        var result = folder_id.hashCode()
        result = 31 * result + folder_name.hashCode()
        result = 31 * result + notes_count.hashCode()
        return result
    }
}