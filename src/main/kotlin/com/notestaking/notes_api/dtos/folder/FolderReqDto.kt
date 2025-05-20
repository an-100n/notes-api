package com.notestaking.notes_api.dtos.folder

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size

data class FolderReqDto(
    @NotBlank(message = "Folder name cannot be blank")
    @Size(max = 150, message = "Folder name must not exceed 150 characters")
    val folderName: String
)