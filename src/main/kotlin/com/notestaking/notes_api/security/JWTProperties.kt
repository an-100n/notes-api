package com.notestaking.notes_api.security

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration


@Configuration
@ConfigurationProperties(prefix = "jwt")
class JWTProperties {
    lateinit var secret: String
}