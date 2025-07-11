package com.notestaking.notes_api.service.folder

import com.notestaking.notes_api.dtos.folder.FolderReqDto
import com.notestaking.notes_api.dtos.folder.FolderResDto
import com.notestaking.notes_api.dtos.note.NoteResDto
import com.notestaking.notes_api.entity.UserEntity
import org.springframework.security.core.Authentication
import java.util.UUID

interface FolderService {
    fun getFolders(auth: Authentication): List<FolderResDto>
    fun getNotesByFolder(folderId: UUID, auth: Authentication): List<NoteResDto>
    fun createFolder(folder: FolderReqDto, auth: Authentication): FolderResDto
    fun createFolder(folder: FolderReqDto, user: UserEntity): FolderResDto
    fun deleteFolder(folderId: UUID, auth: Authentication)
}