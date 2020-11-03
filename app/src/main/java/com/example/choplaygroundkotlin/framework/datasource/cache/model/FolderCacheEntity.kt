package com.example.choplaygroundkotlin.framework.datasource.cache.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "folders")
data class FolderCacheEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: String,

    @ColumnInfo(name = "folder_name")
    var folder_name: String,

    @ColumnInfo(name = "notes_count")
    var notes_count: Int,

    @ColumnInfo(name = "updated_at")
    var updated_at: String,

    @ColumnInfo(name = "created_at")
    var created_at: String

){



    companion object{

        fun nullTitleError(): String{
            return "You must enter a folder_name."
        }

        fun nullIdError(): String{
            return "FolderEntity object has a null id. This should not be possible. Check local database."
        }
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as FolderCacheEntity

        if (id != other.id) return false
        if (folder_name != other.folder_name) return false
        if (notes_count != other.notes_count) return false
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



