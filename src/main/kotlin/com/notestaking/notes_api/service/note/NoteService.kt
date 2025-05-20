package com.notestaking.notes_api.service.note

import com.notestaking.notes_api.dtos.note.NoteReqDto
import com.notestaking.notes_api.dtos.note.NoteResDto
import java.util.*

interface NoteService {
    fun createNote(note: NoteReqDto, userId: UUID): NoteResDto
    fun getNoteByIdAndUser(noteId: UUID, userId: UUID): NoteResDto
    fun deleteNoteByIdAndUser(noteId: UUID, userId: UUID)
}