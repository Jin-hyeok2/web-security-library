package com.example.websecurity.config

import com.example.websecurity.filter.JwtAuthenticationFilter
import com.example.websecurity.status.Role
import com.example.websecurity.utiliy.JwtUtility
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.DefaultSecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.security.web.util.matcher.AntPathRequestMatcher

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true, prePostEnabled = true)
class SecurityConfig(
    private val securityPolicy: SecurityPolicy,
    private val jwtUtility: JwtUtility,
) {

    @Bean
    fun filterChain(http: HttpSecurity): DefaultSecurityFilterChain? {
        return http
            .httpBasic { it.disable() }
            .csrf { it.disable() }
            .sessionManagement {
                it.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            }
            .authorizeHttpRequests {
                it.requestMatchers(*getPermitAllUri(securityPolicy)).anonymous()
                    .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api/v1/**")).authenticated()
            }
            .addFilterBefore(
                JwtAuthenticationFilter(jwtUtility),
                UsernamePasswordAuthenticationFilter::class.java
            )
            .build()
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder =
        PasswordEncoderFactories.createDelegatingPasswordEncoder()


    fun getPermitAllUri(
        securityPolicy: SecurityPolicy
    ): Array<AntPathRequestMatcher> {
        println(securityPolicy.permitAllUri)
        val servletPrefix = securityPolicy.servletPrefix
        return securityPolicy.permitAllUri.map {
            AntPathRequestMatcher.antMatcher(
                (if (servletPrefix == "/") "" else servletPrefix) + it
            )
        }
            .toTypedArray()
    }
}

