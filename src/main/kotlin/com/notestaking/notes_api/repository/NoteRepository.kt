package com.notestaking.notes_api.repository

import com.notestaking.notes_api.entity.NoteEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.Optional
import java.util.UUID

interface NoteRepository : JpaRepository<NoteEntity, UUID> {

    fun findByFolderIdAndOwner_Id(folderId: UUID, userId: UUID): List<NoteEntity>?

    fun findByIdAndOwner_Id(id: UUID, userId: UUID): NoteEntity?

    fun deleteByIdAndOwner_Id(id: UUID, userId: UUID)



}