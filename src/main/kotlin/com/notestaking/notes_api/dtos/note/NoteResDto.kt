package com.notestaking.notes_api.dtos.note

import java.util.UUID

data class NoteResDto(
    val id: UUID,
    val noteTitle: String,
    val noteBody: String,
    val folderId: UUID
)
