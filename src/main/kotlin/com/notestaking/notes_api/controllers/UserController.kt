package com.notestaking.notes_api.controllers

import com.notestaking.notes_api.dtos.user.UserReqDto
import com.notestaking.notes_api.dtos.user.UserResDto
import com.notestaking.notes_api.service.user.UserService
import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
class UserController(
    private val userService: UserService
) {

    private val log = LoggerFactory.getLogger(UserController::class.java)

    @PostMapping("/register")
    fun registerUser(@RequestBody @Valid userReqDto: UserReqDto): ResponseEntity<UserResDto> {
        log.info("Received user registration request for email: {}", userReqDto.email)

        val createdUser = userService.createUser(userReqDto)

        log.info("User created successfully with ID: {}", createdUser.id)

        return ResponseEntity.status(HttpStatus.CREATED).body(createdUser)
    }
}