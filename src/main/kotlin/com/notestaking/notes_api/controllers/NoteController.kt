package com.notestaking.notes_api.controllers

import com.notestaking.notes_api.dtos.note.NoteReqDto
import com.notestaking.notes_api.dtos.note.NoteResDto
import com.notestaking.notes_api.repository.FolderRepository
import com.notestaking.notes_api.service.note.NoteService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*
import jakarta.validation.Valid
import java.util.*
import io.github.oshai.kotlinlogging.KotlinLogging

@RestController
@RequestMapping("/api/v1/notes")
class NoteController(
    private val noteService: NoteService,
    private val folderRepository: FolderRepository
) {
    private val logger = KotlinLogging.logger {}

    @PostMapping
    fun createNote(@RequestBody @Valid noteReqDto: NoteReqDto, auth: Authentication): ResponseEntity<NoteResDto> {
        logger.info { "Attempt to create a note" }
        val createdNote = noteService.createNote(noteReqDto, auth)
        logger.info { "Successfully  created a note" }
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote)
    }

    @GetMapping("/{noteId}")
    fun getNoteById(@PathVariable noteId: UUID, auth: Authentication): ResponseEntity<NoteResDto> {
        logger.info { "Attempt to get a note by ID" }
        val note = noteService.getNoteByIdAndUser(noteId, auth)
        logger.info { "Successfully got a note by ID" }
        return ResponseEntity.ok(note)
    }

    @DeleteMapping("/{noteId}")
    fun deleteNoteById(@PathVariable noteId: UUID, auth: Authentication): ResponseEntity<String> {
        logger.info { "Attempt to delete a note by ID" }
        noteService.deleteNoteByIdAndUser(noteId, auth)
        logger.info { "Successfully deleted the note by ID" }
        return ResponseEntity.ok("Note deleted !!!")
    }
}
