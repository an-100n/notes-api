package com.notestaking.notes_api.security

object SecurityConstants {
    const val EXPIRATION_TIME = 86_400_000L // 1 day in ms
    const val TOKEN_PREFIX = "Bearer "
    const val HEADER_STRING = "Authorization"
    const val REGISTER_PATH = "/api/v1/users/register"
    const val LOGIN_PATH = "/api/v1/users/login"
}