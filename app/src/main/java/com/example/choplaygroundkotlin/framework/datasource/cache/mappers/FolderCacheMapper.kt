package com.example.choplaygroundkotlin.framework.datasource.cache.mappers

import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import com.example.choplaygroundkotlin.business.domain.util.EntityMapper
import com.example.choplaygroundkotlin.framework.datasource.cache.model.FolderCacheEntity
import javax.inject.Inject

/**
 * Maps Folder to FolderCacheEntity or FolderCacheEntity to Folder.
 */
class FolderCacheMapper
@Inject
constructor(
    private val dateUtil: DateUtil
): EntityMapper<FolderCacheEntity, Folder>
{

    fun entityListToFolderList(entities: List<FolderCacheEntity>): List<Folder>{
        val list: ArrayList<Folder> = ArrayList()
        for(entity in entities){
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun folderListToEntityList(folders: List<Folder>): List<FolderCacheEntity>{
        val entities: ArrayList<FolderCacheEntity> = ArrayList()
        for(folder in folders){
            entities.add(mapToEntity(folder))
        }
        return entities
    }

    override fun mapFromEntity(entity: FolderCacheEntity): Folder {
        return Folder(
            id = entity.id,
            folder_name = entity.folder_name,
            notes_count = entity.notes_count,
            updated_at = entity.updated_at,
            created_at = entity.created_at
        )
    }

    override fun mapToEntity(domainModel: Folder): FolderCacheEntity {
        return FolderCacheEntity(
            id = domainModel.id,
            folder_name = domainModel.folder_name,
            notes_count = domainModel.notes_count,
            updated_at = domainModel.updated_at,
            created_at = domainModel.created_at
        )
    }
}







