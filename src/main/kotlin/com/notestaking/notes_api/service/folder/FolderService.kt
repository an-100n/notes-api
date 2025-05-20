package com.notestaking.notes_api.service.folder

import com.notestaking.notes_api.dtos.folder.FolderReqDto
import com.notestaking.notes_api.dtos.folder.FolderResDto
import com.notestaking.notes_api.dtos.note.NoteResDto
import com.notestaking.notes_api.entity.UserEntity
import java.util.UUID

interface FolderService {
    fun createFolder(folder: FolderReqDto, user: UserEntity): FolderResDto
    fun deleteFolder(folderId: UUID, userId: UUID)
    fun getFolders(userId: UUID): List<FolderResDto>
    fun getNotesByFolder(folderId: UUID, userId: UUID): List<NoteResDto>

}