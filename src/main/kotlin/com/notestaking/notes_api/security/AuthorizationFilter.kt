package com.notestaking.notes_api.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.util.*

class AuthorizationFilter(
    authManager: AuthenticationManager,
    private val jwtService: JWTService
) : BasicAuthenticationFilter(authManager) {

    override fun doFilterInternal(req: HttpServletRequest, res: HttpServletResponse, chain: FilterChain) {
        if (isPublicEndpoint(req)) {
            chain.doFilter(req, res)
            return
        }

        val token = extractToken(req.getHeader(SecurityConstants.HEADER_STRING))

        token
            .filter(jwtService::isTokenValid)
            .ifPresent {
                val subject = jwtService.extractSubject(it)
                val authToken = UsernamePasswordAuthenticationToken(subject, null, emptyList())
                SecurityContextHolder.getContext().authentication = authToken
            }

        chain.doFilter(req, res)
    }

    private fun isPublicEndpoint(req: HttpServletRequest): Boolean {
        return req.servletPath in setOf(SecurityConstants.LOGIN_PATH, SecurityConstants.REGISTER_PATH)
    }

    private fun extractToken(header: String?): Optional<String> =
        if (header != null && header.startsWith(SecurityConstants.TOKEN_PREFIX))
            Optional.of(header.removePrefix(SecurityConstants.TOKEN_PREFIX))
        else Optional.empty()
}