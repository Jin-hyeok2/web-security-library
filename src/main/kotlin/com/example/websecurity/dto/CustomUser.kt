package com.example.websecurity.dto

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.User

/**
 * Custom user
 *
 * @param T user id type(Int, Long, UUID...)
 * @property userId
 * @constructor
 *
 * @param userName login id
 * @param password login password
 * @param authorities
 */
class CustomUser<T>(
    val userId: T,
    userName: String,
    password: String,
    authorities: Collection<GrantedAuthority>
) : User(userName, password, authorities)