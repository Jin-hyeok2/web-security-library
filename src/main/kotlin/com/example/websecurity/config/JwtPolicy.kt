package com.example.websecurity.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties("security.jwt")
class JwtPolicy {
    lateinit var accessToken: TokenPolicy
    lateinit var refreshToken: TokenPolicy
}

class TokenPolicy(
    val secretKey: String,
    val expirationTimeMinute: Long
)