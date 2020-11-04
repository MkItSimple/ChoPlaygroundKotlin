package com.example.choplaygroundkotlin.framework.datasource.network.mappers

import com.example.choplaygroundkotlin.business.domain.model.Folder
import com.example.choplaygroundkotlin.business.domain.util.DateUtil
import com.example.choplaygroundkotlin.business.domain.util.EntityMapper
import com.example.choplaygroundkotlin.framework.datasource.network.model.FolderNetworkEntity
import javax.inject.Inject

/**
 * Maps Folder to FolderNetworkEntity or FolderNetworkEntity to Folder.
 */
class FolderNetworkMapper
@Inject
constructor(
    private val dateUtil: DateUtil
): EntityMapper<FolderNetworkEntity, Folder>
{

    fun entityListToFolderList(entities: List<FolderNetworkEntity>): List<Folder>{
        val list: ArrayList<Folder> = ArrayList()
        for(entity in entities){
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun folderListToEntityList(folders: List<Folder>): List<FolderNetworkEntity>{
        val entities: ArrayList<FolderNetworkEntity> = ArrayList()
        for(folder in folders){
            entities.add(mapToEntity(folder))
        }
        return entities
    }

    override fun mapFromEntity(entity: FolderNetworkEntity): Folder {
        return Folder(
            id = entity.id,
            folder_name = entity.folder_name,
            updated_at = dateUtil.convertFirebaseTimestampToStringData(entity.updated_at),
            created_at = dateUtil.convertFirebaseTimestampToStringData(entity.created_at)
        )
    }

    override fun mapToEntity(domainModel: Folder): FolderNetworkEntity {
        return FolderNetworkEntity(
            id = domainModel.id,
            folder_name = domainModel.folder_name,
            updated_at = dateUtil.convertStringDateToFirebaseTimestamp(domainModel.updated_at),
            created_at = dateUtil.convertStringDateToFirebaseTimestamp(domainModel.created_at)
        )
    }


}







