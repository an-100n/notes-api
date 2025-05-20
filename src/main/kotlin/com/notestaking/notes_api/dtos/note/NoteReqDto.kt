package com.notestaking.notes_api.dtos.note

import jakarta.validation.constraints.Size

data class NoteReqDto(
    @field:Size(max = 1000, message = "Note title must not exceed 1000 characters")
    val noteTitle: String,
    @field:Size(max = 6000, message = "Note body must not exceed 6000 characters")
    val noteBody: String,

    val folder:String
)
