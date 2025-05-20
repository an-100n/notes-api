package com.notestaking.notes_api.controllers

import com.notestaking.notes_api.dtos.folder.FolderReqDto
import com.notestaking.notes_api.dtos.folder.FolderResDto
import com.notestaking.notes_api.dtos.note.NoteResDto
import com.notestaking.notes_api.service.folder.FolderService
import com.notestaking.notes_api.service.user.UserService
import jakarta.validation.Valid
import org.hibernate.service.spi.ServiceException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/folder")
class FolderController(
    private val folderService: FolderService,
    private val userService: UserService
) {

    private val log = LoggerFactory.getLogger(FolderController::class.java)

    @GetMapping
    fun getFolders(auth: Authentication): ResponseEntity<List<FolderResDto>> {
        val userId = UUID.fromString(auth.name)
        log.info("Fetching folders for user ID: $userId")
        val folders = folderService.getFolders(userId)
        return ResponseEntity.ok(folders)
    }

    @PostMapping
    fun createFolder(
        @RequestBody @Valid folderReqDto: FolderReqDto,
        auth: Authentication
    ): ResponseEntity<FolderResDto> {
        val userId = UUID.fromString(auth.name)
        val user = userService.findUserById(userId)
            ?: throw ServiceException("User not found with ID: $userId")

        val createdFolder = folderService.createFolder(folderReqDto, user)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFolder)
    }

    @DeleteMapping("/{folderId}")
    fun deleteFolderWithNotes(
        @PathVariable folderId: UUID,
        auth: Authentication
    ): ResponseEntity<String> {
        val userId = UUID.fromString(auth.name)
        log.info("Deleting folder $folderId for user $userId")
        folderService.deleteFolder(folderId, userId)
        return ResponseEntity.ok("Folder deleted successfully")
    }

    @GetMapping("/{folderId}")
    fun getNotesByFolder(
        @PathVariable folderId: UUID,
        auth: Authentication
    ): ResponseEntity<List<NoteResDto>> {
        val userId = UUID.fromString(auth.name)
        log.info("Fetching notes in folder $folderId for user $userId")
        val notes = folderService.getNotesByFolder(folderId, userId)
        return ResponseEntity.ok(notes)
    }
}