package com.example.choplaygroundkotlin.data.network

import com.example.choplaygroundkotlin.data.mappers.EntityMapper
import com.example.choplaygroundkotlin.domain.Note
import javax.inject.Inject

class NetworkMapper
@Inject
constructor(
): EntityMapper<NoteNetworkEntity, Note>
{

    fun entityListToNoteList(entities: List<NoteNetworkEntity>): List<Note>{
        val list: ArrayList<Note> = ArrayList()
        for(entity in entities){
            list.add(mapFromEntity(entity))
        }
        return list
    }

    fun noteListToEntityList(notes: List<Note>): List<NoteNetworkEntity>{
        val entities: ArrayList<NoteNetworkEntity> = ArrayList()
        for(note in notes){
            entities.add(mapToEntity(note))
        }
        return entities
    }

    override fun mapFromEntity(entity: NoteNetworkEntity): Note {
        return Note(
            id = entity.id,
            title = entity.title,
            note_folder_id = entity.note_folder_id
//            body = entity.body,
//            updated_at = dateUtil.convertFirebaseTimestampToStringData(entity.updated_at),
//            created_at = dateUtil.convertFirebaseTimestampToStringData(entity.created_at)
        )
    }

    override fun mapToEntity(domainModel: Note): NoteNetworkEntity {
        return NoteNetworkEntity(
            id = domainModel.id,
            title = domainModel.title,
            note_folder_id = domainModel.note_folder_id
//            body = domainModel.body,
//            updated_at = dateUtil.convertStringDateToFirebaseTimestamp(domainModel.updated_at),
//            created_at = dateUtil.convertStringDateToFirebaseTimestamp(domainModel.created_at)
        )
    }


}