package com.notestaking.notes_api.security

import com.fasterxml.jackson.databind.ObjectMapper
import com.notestaking.notes_api.dtos.user.LoginRequestDto
import com.notestaking.notes_api.dtos.user.UserReqDto
import com.notestaking.notes_api.service.user.CustomUserDetails
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


class AuthenticationFilter (
    authManager: AuthenticationManager,
    private val jwtService: JWTService
) : UsernamePasswordAuthenticationFilter(authManager) {

    init {
        setFilterProcessesUrl(SecurityConstants.LOGIN_PATH)
    }

    override fun attemptAuthentication(req: HttpServletRequest, res: HttpServletResponse): Authentication {
        println("AuthenticationFilter -> attemptAuthentication")
        val credentials = ObjectMapper().readValue(req.inputStream, LoginRequestDto::class.java)
        val token = UsernamePasswordAuthenticationToken(credentials.email, credentials.password)
        return authenticationManager.authenticate(token)
    }

    override fun successfulAuthentication(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain,
        auth: Authentication
    ) {
        println("AuthenticationFilter -> successfulAuthentication")
        val user = auth.principal as CustomUserDetails
        val token = jwtService.generateToken(user.id.toString())
        res.addHeader(SecurityConstants.HEADER_STRING, SecurityConstants.TOKEN_PREFIX + token)
    }
}