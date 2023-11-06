package com.example.websecurity.service

import com.example.websecurity.dto.CustomUser
import com.example.websecurity.entity.CustomUserEntity
import com.example.websecurity.repository.CustomUserRepository
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

class CustomUserDetailService(
    private val customUserRepository: CustomUserRepository
): UserDetailsService {
    override fun loadUserByUsername(username: String): UserDetails =
        customUserRepository.findByLoginId(username)
            ?.let { createUserDetails(it) } ?: throw UsernameNotFoundException("해당 유저는 없습니다")

    private fun createUserDetails(member: CustomUserEntity<*>) =
        CustomUser(
            member.id(),
            member.loginId(),
            member.encodedPassword(),
            member.roles().map { SimpleGrantedAuthority(it.name) }
        )
}