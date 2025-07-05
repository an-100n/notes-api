package com.notestaking.notes_api.service.note

import com.notestaking.notes_api.dtos.note.NoteReqDto
import com.notestaking.notes_api.dtos.note.NoteResDto
import org.springframework.security.core.Authentication
import java.util.*

interface NoteService {
    fun createNote(note: NoteReqDto, auth: Authentication): NoteResDto
    fun getNoteByIdAndUser(noteId: UUID, auth: Authentication): NoteResDto
    fun deleteNoteByIdAndUser(noteId: UUID, auth: Authentication)
}