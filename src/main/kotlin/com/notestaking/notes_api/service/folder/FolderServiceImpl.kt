package com.notestaking.notes_api.service.folder

import com.notestaking.notes_api.exceptions.ResourceNotFoundException
import com.notestaking.notes_api.dtos.folder.FolderReqDto
import com.notestaking.notes_api.dtos.folder.FolderResDto
import com.notestaking.notes_api.dtos.note.NoteResDto
import com.notestaking.notes_api.entity.FolderEntity
import com.notestaking.notes_api.exceptions.ServiceException
import com.notestaking.notes_api.repository.FolderRepository
import com.notestaking.notes_api.repository.NoteRepository
import com.notestaking.notes_api.service.user.UserService
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import java.util.*

@Service
class FolderServiceImpl(
    private val folderRepository: FolderRepository,
    private val noteRepository: NoteRepository,
    private val userService: UserService
) : FolderService {

    private val log = LoggerFactory.getLogger(FolderServiceImpl::class.java)

    override fun createFolder(folder: FolderReqDto, auth: Authentication): FolderResDto {
        val userId = UUID.fromString(auth.name)
        val user = userService.findUserById(userId)
            ?: throw ServiceException("User not found with ID: $userId")
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

    override fun deleteFolder(folderId: UUID, auth: Authentication) {
        val userId = UUID.fromString(auth.name)
        if (!folderRepository.existsById(folderId)) {
            throw ResourceNotFoundException("Folder not found, cannot be deleted")
        }

        try {
            folderRepository.deleteByIdAndUser_Id(folderId, userId)
        } catch (e: Exception) {
            throw ServiceException("Error while deleting folder: ${e.message}")
        }
    }

    override fun getFolders(auth: Authentication): List<FolderResDto> {
        val userId = UUID.fromString(auth.name)
        val folders = folderRepository.getFoldersByUser_Id(userId)
            ?: throw NoSuchElementException("No folders found for user ID: $userId")

        return folders.map {
            FolderResDto(
                id = it.id!!,
                folderName = it.folderName
            )
        }
    }



    override fun getNotesByFolder(folderId: UUID, auth: Authentication): List<NoteResDto> {
        val userId = UUID.fromString(auth.name)
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