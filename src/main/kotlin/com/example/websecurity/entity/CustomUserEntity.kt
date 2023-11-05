package com.example.websecurity.entity

import com.example.websecurity.status.Role

interface CustomUserEntity<T> {
    fun id(): T
    fun loginId(): String
    fun encodedPassword(): String
    fun roles(): Collection<Role>
}