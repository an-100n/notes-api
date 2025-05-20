package com.notestaking.notes_api.repository

import com.notestaking.notes_api.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository
import java.util.UUID

interface UserRepository : JpaRepository<UserEntity, UUID> {
    fun findByEmail(email: String): UserEntity?
    fun findByUsername(username: String): UserEntity?
}