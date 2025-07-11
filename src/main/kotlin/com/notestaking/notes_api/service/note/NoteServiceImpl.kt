package com.notestaking.notes_api.service.note

import com.notestaking.notes_api.dtos.note.NoteReqDto
import com.notestaking.notes_api.dtos.note.NoteResDto
import com.notestaking.notes_api.entity.FolderEntity
import com.notestaking.notes_api.entity.NoteEntity
import com.notestaking.notes_api.entity.UserEntity
import com.notestaking.notes_api.exceptions.ResourceNotFoundException
import com.notestaking.notes_api.repository.FolderRepository
import com.notestaking.notes_api.repository.NoteRepository
import com.notestaking.notes_api.repository.UserRepository
import org.slf4j.LoggerFactory
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*

@Service
class NoteServiceImpl(
    private val noteRepository: NoteRepository,
    private val folderRepository: FolderRepository,
    private val userRepository: UserRepository
) : NoteService {

    private val log = LoggerFactory.getLogger(NoteServiceImpl::class.java)

    override fun createNote(note: NoteReqDto, auth: Authentication): NoteResDto {
        //log.info("📌 Creating a note for user ID: $userId")
        val userId = UUID.fromString(auth.name)
        val user = userRepository.findById(userId).orElse(null)
            ?: throw ResourceNotFoundException("User not found")

        val folder = getOrCreateFolder(note.folder, user)

        val note = NoteEntity(
            owner = user,
            noteTitle = note.noteTitle,
            noteBody = note.noteBody,
            folder = folder
        )

        val savedNote = noteRepository.save(note)

        log.info("✅ Note saved with ID: ${savedNote.id}")

        return NoteResDto(
            id = savedNote.id!!,
            noteTitle = savedNote.noteTitle,
            noteBody = savedNote.noteBody,
            folderId = folder.id!!
        )
    }

    override fun getNoteByIdAndUser(noteId: UUID, auth: Authentication): NoteResDto {

        val userId = UUID.fromString(auth.name)
        val note = noteRepository.findByIdAndOwner_Id(noteId, userId)
            ?: throw ResourceNotFoundException("Note not found for the given ID.")

        return NoteResDto(
            id = note.id!!,
            noteTitle = note.noteTitle,
            noteBody = note.noteBody,
            folderId = note.folder?.id!!
        )
    }

    @Transactional
    override fun deleteNoteByIdAndUser(noteId: UUID, auth: Authentication) {
        val userId = UUID.fromString(auth.name)
         noteRepository.findByIdAndOwner_Id(noteId, userId)
            ?: throw ResourceNotFoundException("Note not found for the given ID.")

        noteRepository.deleteByIdAndOwner_Id(noteId, userId)
    }

    private fun getOrCreateFolder(folderName: String?, user: UserEntity): FolderEntity {
        return if (folderName.isNullOrBlank()) {
            folderRepository.findByFolderNameAndUser("default", user)
                ?: throw ResourceNotFoundException("Default folder not found")
        } else {
            folderRepository.findByFolderNameAndUser(folderName, user)
                ?: folderRepository.save(FolderEntity(folderName, user, mutableListOf()))
        }
    }
}