package com.example.choplaygroundkotlin.data.local

import com.example.choplaygroundkotlin.data.mappers.EntityMapper
import com.example.choplaygroundkotlin.domain.Note
import javax.inject.Inject

/**
 * Maps Note to NoteCacheEntity or NoteCacheEntity to Note.
 */
class CacheMapper
@Inject
constructor(): EntityMapper<NoteCacheEntity, Note>
{

    fun entityListToNoteList(entities: List<NoteCacheEntity>): List<Note>{
        val list: ArrayList<Note> = ArrayList()
        for(entity in entities){
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun noteListToEntityList(notes: List<Note>): List<NoteCacheEntity>{
        val entities: ArrayList<NoteCacheEntity> = ArrayList()
        for(note in notes){
            entities.add(mapToEntity(note))
        }
        return entities
    }

    override fun mapFromEntity(entity: NoteCacheEntity): Note {
        return Note(
            id = entity.id,
            title = entity.title,
            note_folder_id = entity.note_folder_id
//            body = entity.body,
//            updated_at = entity.updated_at,
//            created_at = entity.created_at
        )
    }

    override fun mapToEntity(domainModel: Note): NoteCacheEntity {
        return NoteCacheEntity(
            id = domainModel.id,
            title = domainModel.title,
            note_folder_id = domainModel.note_folder_id
//            body = domainModel.body,
//            updated_at = domainModel.updated_at,
//            created_at = domainModel.created_at
        )
    }
}