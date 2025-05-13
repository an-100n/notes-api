package com.notestaking.notes_api.dtos.user

import java.util.UUID

data class UserResDto(
    val id: UUID,
    val username:String,
    val email:String
)
