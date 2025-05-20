package com.notestaking.notes_api.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.stereotype.Service
import java.util.Date
import javax.crypto.SecretKey

@Service
class JWTService(
    private val jwtProperties: JWTProperties
) {

    private lateinit var secretKey: SecretKey

    @PostConstruct
    fun init() {
        secretKey = Keys.hmacShaKeyFor(jwtProperties.secret.toByteArray())
    }

    fun generateToken(subject: String): String {
        println("Generating JWT token")
        return Jwts.builder()
            .setSubject(subject)
            .setIssuedAt(Date())
            .setExpiration(Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
            .signWith(secretKey)
            .compact()
    }

    fun isTokenValid(token: String): Boolean {
        println("Is Token Valid -> ")
        return try {
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token)
            println("YES")
            true
        } catch (e: Exception) {
            println("NO")
            false
        }
    }

    fun extractClaims(token: String): Claims {
        return Jwts.parserBuilder()
            .setSigningKey(secretKey)
            .build()
            .parseClaimsJws(token)
            .body
    }

    fun extractSubject(token: String): String {
        return extractClaims(token).subject
    }
}