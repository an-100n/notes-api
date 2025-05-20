package com.notestaking.notes_api.dtos.folder

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class FolderReqDto(
    @field:NotBlank(message = "Folder name cannot be blank")
    @field:Size(max = 150, message = "Folder name must not exceed 150 characters")
    val folderName: String
)