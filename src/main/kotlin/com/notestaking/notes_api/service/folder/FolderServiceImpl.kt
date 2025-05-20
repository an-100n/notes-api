package com.notestaking.notes_api.service.folder

import com.notestaking.notes_api.exceptions.ResourceNotFoundException
import com.notestaking.notes_api.dtos.folder.FolderReqDto
import com.notestaking.notes_api.dtos.folder.FolderResDto
import com.notestaking.notes_api.dtos.note.NoteResDto
import com.notestaking.notes_api.entity.FolderEntity
import com.notestaking.notes_api.entity.UserEntity
import com.notestaking.notes_api.repository.FolderRepository
import com.notestaking.notes_api.repository.NoteRepository
import org.hibernate.service.spi.ServiceException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import java.util.*

@Service
class FolderServiceImpl(
    private val folderRepository: FolderRepository,
    private val noteRepository: NoteRepository
) : FolderService {

    private val log = LoggerFactory.getLogger(FolderServiceImpl::class.java)

    override fun createFolder(folder: FolderReqDto, user: UserEntity): FolderResDto {
        log.info("FolderServiceImpl -> createFolder {}", user)

        val folderEntity = FolderEntity(
            folderName = folder.folderName,
            user = user
        )

        val savedFolder = folderRepository.save(folderEntity)

        return FolderResDto(
            id = savedFolder.id!!,
            folderName = savedFolder.folderName
        )
    }

    override fun deleteFolder(folderId: UUID, userId: UUID) {
        if (!folderRepository.existsById(folderId)) {
            throw ResourceNotFoundException("Folder not found, cannot be deleted")
        }

        try {
            folderRepository.deleteByIdAndUser_Id(folderId, userId)
        } catch (e: Exception) {
            throw ServiceException("Error while deleting folder: ${e.message}", e) as Throwable
        }
    }

    override fun getFolders(userId: UUID): List<FolderResDto> {
        val folders = folderRepository.getFoldersByUser_Id(userId)
            ?: throw NoSuchElementException("No folders found for user ID: $userId")

        return folders.map {
            FolderResDto(
                id = it.id!!,
                folderName = it.folderName
            )
        }
    }



    override fun getNotesByFolder(folderId: UUID, userId: UUID): List<NoteResDto> {
        val notes = noteRepository.findByFolderIdAndOwner_Id(folderId, userId)
            ?: throw ResourceNotFoundException("No notes found in folder: $folderId")

        return notes.map {
            NoteResDto(
                id = it.id!!,
                noteTitle = it.noteTitle,
                noteBody = it.noteBody,
                folderId = it.folder.id!!
            )
        }
    }
}