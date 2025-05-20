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

@RestController
@RequestMapping("/api/notes")
class NoteController(
    private val noteService: NoteService,
    private val folderRepository: FolderRepository
) {

    @PostMapping("/create")
    fun createNote(
        @RequestBody @Valid noteReqDto: NoteReqDto,
        auth: Authentication
    ): ResponseEntity<NoteResDto> {
        val userId = UUID.fromString(auth.name)
        val createdNote = noteService.createNote(noteReqDto, userId)
        return ResponseEntity.status(HttpStatus.CREATED).body(createdNote)
    }

    @GetMapping("/{noteId}")
    fun getNoteById(
        @PathVariable noteId: UUID,
        auth: Authentication
    ): ResponseEntity<NoteResDto> {
        val userId = UUID.fromString(auth.name)
        val note = noteService.getNoteByIdAndUser(noteId, userId)
        return ResponseEntity.ok(note)
    }

    @DeleteMapping("/{noteId}")
    fun deleteNoteById(
        @PathVariable noteId: UUID,
        auth: Authentication
    ): ResponseEntity<String> {
        val userId = UUID.fromString(auth.name)
        noteService.deleteNoteByIdAndUser(noteId, userId)
        return ResponseEntity.ok("Note deleted !!!")
    }
}
