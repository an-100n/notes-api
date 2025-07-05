package com.notestaking.notes_api.controllers

import com.notestaking.notes_api.dtos.folder.FolderReqDto
import com.notestaking.notes_api.dtos.folder.FolderResDto
import com.notestaking.notes_api.dtos.note.NoteResDto
import com.notestaking.notes_api.service.folder.FolderService
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import java.util.*

@RestController
@RequestMapping("/api/v1/folders")
class FolderController(
    private val folderService: FolderService,

    ) {
    private val logger = KotlinLogging.logger {}

    @GetMapping
    fun getFolders(auth: Authentication): ResponseEntity<List<FolderResDto>> {
        logger.info { "Attempt to get folders" }
        val folders = folderService.getFolders(auth)
        logger.info { "Successfully got folders" }
        return ResponseEntity.ok(folders)
    }

    @PostMapping
    fun createFolder(
        @RequestBody @Valid folderReqDto: FolderReqDto,
        auth: Authentication
    ): ResponseEntity<FolderResDto> {
        logger.info { "Attempt to create a folder" }
        val createdFolder = folderService.createFolder(folderReqDto, auth)
        logger.info { "Successfully created a folder" }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdFolder)
    }

    @DeleteMapping("/{folderId}")
    fun deleteFolderWithNotes(@PathVariable folderId: UUID, auth: Authentication): ResponseEntity<String> {
        logger.info { "Attempt to delete a folder by ID" }
        folderService.deleteFolder(folderId, auth)
        logger.info { "Successfully deleted a folder by ID" }
        return ResponseEntity.ok("Folder deleted successfully")
    }

    @GetMapping("/{folderId}")
    fun getNotesByFolder(@PathVariable folderId: UUID, auth: Authentication): ResponseEntity<List<NoteResDto>> {
        logger.info { "Attempt to get a folder by ID" }
        val notes = folderService.getNotesByFolder(folderId, auth)
        logger.info { "Successfully get a folder by ID" }
        return ResponseEntity.ok(notes)
    }
}