package com.notestaking.notes_api.service.user

import com.notestaking.notes_api.dtos.user.UserReqDto
import com.notestaking.notes_api.dtos.user.UserResDto
import com.notestaking.notes_api.entity.UserEntity
import org.springframework.security.core.userdetails.UserDetailsService
import java.util.UUID

interface UserService: UserDetailsService {
    fun createUser(user: UserReqDto): UserResDto
    fun findUserById(userId: UUID): UserEntity?
}