package com.notestaking.notes_api.service.user

import com.notestaking.notes_api.dtos.folder.FolderReqDto
import com.notestaking.notes_api.dtos.user.UserReqDto
import com.notestaking.notes_api.dtos.user.UserResDto
import com.notestaking.notes_api.entity.UserEntity
import com.notestaking.notes_api.exceptions.ResourceNotFoundException
import com.notestaking.notes_api.exceptions.DuplicateResourceException
import com.notestaking.notes_api.repository.UserRepository
import com.notestaking.notes_api.service.folder.FolderService
import jakarta.transaction.Transactional
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val folderService: FolderService
) : UserService {

    @Transactional
    override fun createUser(user: UserReqDto): UserResDto {
        if (userRepository.findByEmail(user.email) != null) {
            throw DuplicateResourceException("User with this email already exists: ${user.email}")
        }
        if (userRepository.findByUsername(user.username) != null) {
            throw DuplicateResourceException("Username '${user.username}' is already taken")
        }


        val userEntity = UserEntity(
            email = user.email,
            username = user.username,
            password = bCryptPasswordEncoder.encode(user.password)
        )

        userRepository.save(userEntity)
        createDefaultFolder(userEntity)

        return UserResDto(
            id = userEntity.id!!,
            username = userEntity.username,
            email = userEntity.email
        )
    }

    override fun loadUserByUsername(email: String): UserDetails {
        val user = userRepository.findByEmail(email)
            ?: throw ResourceNotFoundException("User not found with identifier: $email")
        return CustomUserDetails(
            id = user.id!!,
            email = user.email,
            realUsername = user.username,
            password = user.password
        )
        //CustomUserDetails made some chenges
//        return CustomUserDetails(
//            id = user.id!!,
//            email = user.email,
//            username = user.username,
//            password = user.password
//        )
    }

    override fun findUserById(userId: UUID): UserEntity? =
        userRepository.findById(userId).orElse(null)

    private fun createDefaultFolder(user: UserEntity) {
        val defaultFolder = FolderReqDto(folderName = "default")
        folderService.createFolder(defaultFolder, user)
    }
}