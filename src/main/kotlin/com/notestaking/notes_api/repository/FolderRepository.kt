package com.notestaking.notes_api.repository

import com.notestaking.notes_api.entity.FolderEntity
import com.notestaking.notes_api.entity.UserEntity
import jakarta.transaction.Transactional
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface FolderRepository : JpaRepository<FolderEntity, UUID> {

    fun findByFolderNameAndUser(folderName: String, user: UserEntity): FolderEntity?

    @Transactional
    fun deleteByIdAndUser_Id(id: UUID, user_id: UUID)

    fun getFoldersByUser_Id(user_id: UUID): List<FolderEntity>?


}