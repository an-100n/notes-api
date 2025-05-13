package com.notestaking.notes_api.dtos.user

import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class UserReqDto(
    @NotBlank(message = "Username is required")
    @Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    val username: String,

    @NotBlank(message = "Email is required")
    @Email(message = "Email is invalid")
    val email: String,

    @NotBlank(message = "Password is required")
    @Size(min = 6, message = "Password must be at least 6 characters long")
    val password: String
)