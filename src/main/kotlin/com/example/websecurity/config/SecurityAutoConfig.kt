package com.example.websecurity.config

import com.example.websecurity.repository.CustomUserRepository
import com.example.websecurity.repository.DefaultCustomUserRepository
import com.example.websecurity.service.CustomUserDetailService
import com.example.websecurity.utiliy.EncryptEncoder
import com.example.websecurity.utiliy.JwtUtility
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.AutoConfiguration
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.context.annotation.Bean

@AutoConfiguration
@ConditionalOnProperty(name = ["security"], havingValue = "on")
@ConditionalOnMissingBean(CustomUserRepository::class)
class SecurityAutoConfig {

    @Bean
    fun customUserRepository(): CustomUserRepository =
        DefaultCustomUserRepository()

    @Bean
    fun customUserDetailService(): CustomUserDetailService =
        CustomUserDetailService(customUserRepository())

    @Bean
    fun jwtPolicy(): JwtPolicy = JwtPolicy()
    @Bean
    fun securityPolicy(): SecurityPolicy = SecurityPolicy()

    @Value("\${security.encoderSecret}")
    lateinit var encoderSecret: String

    @Bean
    fun encryptEncoder(): EncryptEncoder =
        EncryptEncoder(encoderSecret)
    @Bean
    fun jwtUtility(): JwtUtility =
        JwtUtility(jwtPolicy(), encryptEncoder())
}