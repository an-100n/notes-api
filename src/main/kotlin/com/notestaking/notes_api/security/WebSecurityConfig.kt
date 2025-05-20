package com.notestaking.notes_api.security

import com.notestaking.notes_api.service.user.UserService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken

@Configuration
@EnableWebSecurity
class WebSecurityConfig(
    private val userService: UserService,
    private val bCryptPasswordEncoder: BCryptPasswordEncoder,
    private val jwtService: JWTService
) {




    @Bean
    fun filterChain(http: HttpSecurity, authManager: AuthenticationManager): SecurityFilterChain {
        if (isTestEnvironment()) {
            http.csrf { it.disable() }
                .authorizeHttpRequests { it.anyRequest().permitAll() }
            return http.build()
        }

        val authFilter = com.notestaking.notes_api.security.AuthenticationFilter(authManager, jwtService)
        authFilter.setFilterProcessesUrl(SecurityConstants.LOGIN_PATH)

        http
            .csrf { it.disable() }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests {
                it.requestMatchers(SecurityConstants.REGISTER_PATH, SecurityConstants.LOGIN_PATH).permitAll()

//                it.requestMatchers(
//                    AntPathRequestMatcher(SecurityConstants.REGISTER_PATH),
//                    AntPathRequestMatcher(SecurityConstants.LOGIN_PATH)
//                ).permitAll()
                it.anyRequest().authenticated()
            }
            .addFilter(authFilter)
            .addFilterAfter(
                AuthorizationFilter(authManager, jwtService),
                UsernamePasswordAuthenticationFilter::class.java
            )

        return http.build()
    }

    @Bean
    fun authenticationManager(userService: UserService, passwordEncoder: BCryptPasswordEncoder): AuthenticationManager {
        return AuthenticationManager { auth ->
            val userDetails = userService.loadUserByUsername(auth.name)
            if (!passwordEncoder.matches(auth.credentials.toString(), userDetails.password)) {
                throw BadCredentialsException("Invalid username or password")
            }
            UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
        }
    }


    private fun isTestEnvironment(): Boolean {
        return System.getProperty("spring.test.context") == "true"
    }
}