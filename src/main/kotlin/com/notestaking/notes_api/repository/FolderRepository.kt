package com.notestaking.notes_api.repository

import com.notestaking.notes_api.entity.FolderEntity
import com.notestaking.notes_api.entity.NoteEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface FolderRepository : JpaRepository<FolderEntity, UUID> {

    @Transactional
    fun deleteByIdAndUser_Id(id: UUID, user_id: UUID)

    fun getFoldersByUser_Id(user_id: UUID): Optional<List<NoteEntity>>

    //Optional<List<NoteEntity>> getNotesByIdAndUserId(UUID id, UUID user_id);


}