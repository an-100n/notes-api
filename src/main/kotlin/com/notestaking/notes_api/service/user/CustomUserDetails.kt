package com.notestaking.notes_api.service.user

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.util.UUID

class CustomUserDetails(
    val id: UUID,
    val email: String,
    val username: String,
    private val password: String
) : UserDetails {

    override fun getPassword(): String = password

    // Spring uses this as the "username" during login — we return email
    override fun getUsername(): String = email

    fun getRealUsername(): String = username

    override fun getAuthorities(): Collection<GrantedAuthority> = emptyList()

    override fun isAccountNonExpired(): Boolean = true

    override fun isAccountNonLocked(): Boolean = true

    override fun isCredentialsNonExpired(): Boolean = true

    override fun isEnabled(): Boolean = true
}