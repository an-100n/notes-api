package com.notestaking.notes_api.exceptions

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.MethodArgumentNotValidException

@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException::class)
    fun handleNotFound(ex: ResourceNotFoundException): ResponseEntity<Map<String, String>> {
        val body: Map<String, String> = mapOf("error" to (ex.message ?: "Resource not found"))
        return ResponseEntity<Map<String, String>>(body, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(DuplicateResourceException::class)
    fun handleDuplicate(ex: DuplicateResourceException): ResponseEntity<Map<String, String>> {
        val body: Map<String, String> = mapOf("error" to (ex.message ?: "Duplicate resource"))
        return ResponseEntity<Map<String, String>>(body, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(Exception::class)
    fun handleGeneric(ex: Exception): ResponseEntity<Map<String, String>> {
        val body: Map<String, String> = mapOf("error" to "Something went wrong")
        return ResponseEntity<Map<String, String>>(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }
    @ExceptionHandler(ServiceException::class)
    fun handleServiceError(ex: ServiceException): ResponseEntity<Map<String, String>> {
        val body: Map<String, String> = mapOf("error" to (ex.message ?: "Internal service error"))
        return ResponseEntity(body, HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @RestControllerAdvice
    class GlobalExceptionHandler {

        @ExceptionHandler(MethodArgumentNotValidException::class)
        fun handleValidation(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
            val errors = ex.bindingResult.fieldErrors.associate { error ->
                error.field to (error.defaultMessage ?: "Invalid value")
            }

            val responseBody = mapOf(
                "error" to "Validation failed",
                "details" to errors
            )

            return ResponseEntity(responseBody, HttpStatus.BAD_REQUEST)
        }
    }

}