package com.example.choplaygroundkotlin.business.domain.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Folder(
    val id: String,
    val folder_name: String,
    val notes_count: Int
) : Parcelable{

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Folder

        if (id != other.id) return false
        if (folder_name != other.folder_name) return false
        if (notes_count != other.notes_count) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + folder_name.hashCode()
        result = 31 * result + notes_count.hashCode()
        return result
    }
}